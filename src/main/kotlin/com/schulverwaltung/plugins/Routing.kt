package com.schulverwaltung.plugins

import com.schulverwaltung.controllers.interfaces.ISecretaryController
import com.schulverwaltung.controllers.interfaces.ISecretarySecretController
import com.schulverwaltung.controllers.interfaces.IStudentController
import com.schulverwaltung.controllers.interfaces.IStudentSecretController
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.utils.CsvReader
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.velocity.*
import org.koin.ktor.ext.inject
import java.io.File

enum class UserType {
    Secretary,
    Student,
    None
}

data class UserSession(val token: String) : Principal
data class UserInfo(val userName: String, val type: UserType, val userId: Int)

class UserPrincipal(val userName: String, val type: UserType, val userId: Int) : Principal

fun Application.configureRouting() {
    val secretarySecretController by inject<ISecretarySecretController>()
    val studentsSecretController by inject<IStudentSecretController>()
    val studentController by inject<IStudentController>()
    val secretaryController by inject<ISecretaryController>()
    val passwordHasher by inject<IPasswordHasher>()

    val tokenList = mutableMapOf<String, UserInfo>()

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60
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
                call.respondRedirect("/login")
                //TODO("Redirect to login false page")
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
        get("/addUsers") {
            //studentController.add(Student(0, "Christian", "Zahn", "IFA12a", "12.12.2003", "christian.zahn@yahooo.com"))
            //studentController.add(Student(0, "Toan", "Bui", "IFA13a", "12.12.2003", "toan.bui@yahooo.com"))
            secretaryController.add(Secretary(0, "Secretary", "VanHil"))
        }
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
                call.respondText("Hello, ${call.principal<UserPrincipal>()?.userName}!")
            }
        }
        authenticate("auth-session") {
            route("/secretary") {
                get {
                    val token = call.sessions.get<UserSession>()?.token
                    if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Secretary)) {
                        val secretary = secretaryController.getById(tokenList[token]?.userId!!)!!
                        call.respond(VelocityContent("templates/secretary.vm", mapOf("secretary" to secretary)))
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

                                    val fileBytes = partData.streamProvider()
                                    val parseData = CsvReader.readCsv(fileBytes)
                                    studentController.deleteAll()
                                    studentsSecretController.deleteAll()
                                    studentController.add(parseData)
                                    println("Test:" + parseData)

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