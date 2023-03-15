package com.schulverwaltung.plugins

import com.schulverwaltung.authentication.UserInfo
import com.schulverwaltung.authentication.UserPrincipal
import com.schulverwaltung.authentication.UserSession
import com.schulverwaltung.authentication.UserType
import com.schulverwaltung.controllers.interfaces.*
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.utils.CsvReader
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.velocity.*
import org.koin.ktor.ext.inject
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun Application.configureRouting() {
    val secretarySecretController by inject<ISecretarySecretController>()
    val studentsSecretController by inject<IStudentSecretController>()
    val studentController by inject<IStudentController>()
    val secretaryController by inject<ISecretaryController>()
    val csvImportHistoryController by inject<ICsvHistoryController>()
    val passwordHasher by inject<IPasswordHasher>()


    val tokenList = mutableMapOf<String, UserInfo>()

    install(StatusPages) {

        status(HttpStatusCode.NotFound) { call, status ->
            call.respondRedirect("/error")
        }

    }

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 600
        }
    }

    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                var type: UserType = UserType.None

                var secret = secretarySecretController.getByUserName(credentials.name)
                if (secret != null) {
                    type = UserType.Secretary
                } else {
                    secret = studentsSecretController.getByUserName(credentials.name)
                    print(secret)
                    if (secret != null) type = UserType.Student
                }

                if (type == UserType.None) return@validate null

                if (passwordHasher.verifyPassword(credentials.password, secret!!.hash)) {
                    UserPrincipal(credentials.name, type, secret.userId)
                } else null
            }
            challenge {
                call.respond(VelocityContent("templates/login-error.vm", mapOf()))
            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                if (tokenList.containsKey(session.token)) session else null
            }
            challenge {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/login")
            }
        }
    }
    routing {
        authenticate("auth-form", strategy = AuthenticationStrategy.Required) {
            post("/login") {
                val userName = call.principal<UserPrincipal>()?.userName.toString()
                val type = call.principal<UserPrincipal>()?.type
                val userId = call.principal<UserPrincipal>()?.userId

                val token = passwordHasher.hashPassword(userName)

                tokenList[token] = UserInfo(userName, type ?: UserType.None, userId!!)

                call.sessions.set(UserSession(token = token))


                when (type) {
                    UserType.Student -> call.respondRedirect("/student")
                    UserType.Secretary -> call.respondRedirect("/secretary")
                    else -> {
                        call.respondRedirect("/logout")
                    }
                }
            }
        }
        authenticate("auth-session") {
            route("/secretary") {
                get {
                    val token = call.sessions.get<UserSession>()?.token
                    if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Secretary)) {
                        val secretary = secretaryController.getById(tokenList[token]?.userId!!)!!
                        val csvImportHistory = csvImportHistoryController.getLatest() ?: CSVImportHistory(0, "", "")
                        call.respond(
                            VelocityContent(
                                "templates/secretary.vm",
                                mapOf("secretary" to secretary, "csvImportHistory" to csvImportHistory)
                            )
                        )
                    } else {
                        call.respondRedirect("/logout")
                    }
                }
                post("/upload") {
                    val token = call.sessions.get<UserSession>()?.token
                    if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Secretary)) {

                        val multipartData = call.receiveMultipart()

                        multipartData.forEachPart { partData ->
                            when (partData) {
                                is PartData.FileItem -> {

                                    val fileName = partData.originalFileName.orEmpty().toString()
                                    val fileBytes = partData.streamProvider()
                                    val parseData = CsvReader.readCsv(fileBytes)
                                    studentsSecretController.deleteAll()
                                    studentController.deleteAll()
                                    studentController.add(parseData)
                                    csvImportHistoryController.add(
                                        CSVImportHistory(
                                            0,
                                            LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")),
                                            fileName
                                        )
                                    )
                                    call.respondRedirect("/secretary")
                                }

                                else -> {}
                            }
                        }
                    } else {
                        call.respondRedirect("/logout")
                    }
                }
            }
            get("/student") {
                val token = call.sessions.get<UserSession>()?.token
                if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Student)) {
                    val student = studentController.getById(tokenList[token]?.userId!!)!!
                    call.respond(VelocityContent("templates/students.vm", mapOf("student" to student)))
                } else {
                    call.respondRedirect("/logout")
                }
            }
        }

        get("/") {
            call.respondRedirect("/login")
        }

        get("/error") {
            call.respond(VelocityContent("templates/error.vm", mapOf()))
        }

        get("/login") {
            call.respond(VelocityContent("templates/login.vm", mapOf()))
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
        static("assets") {
            staticRootFolder = File("src/main/resources/assets")
            files(".")
        }
    }
}