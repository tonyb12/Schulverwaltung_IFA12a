package com.example.plugins

import com.example.controllers.SecretaryController
import com.example.controllers.SecretarySecretController
import com.example.controllers.StudentController
import com.example.controllers.StudentsSecretController
import com.example.dto.Student
import com.example.utils.PasswordHasher
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.Authentication
import io.ktor.server.http.content.*
import io.ktor.server.sessions.*
import io.ktor.server.velocity.*
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
    val secretarySecretController = SecretarySecretController()
    val studentsSecretController = StudentsSecretController()
    val studentController = StudentController()
    val secretaryController = SecretaryController()

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

                if (PasswordHasher.verifyPassword(credentials.password, secret!!.hash)) {
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
            studentController.add(Student(0, "Christian", "Zahn", "IFA12a", "12.12.2003", "christian.zahn@yahooo.com"))
            studentController.add(Student(0, "Toan", "Bui", "IFA13a", "12.12.2003", "toan.bui@yahooo.com"))
        }
        authenticate("auth-form", strategy = AuthenticationStrategy.Required) {
            post("/login") {
                val userName = call.principal<UserPrincipal>()?.userName.toString()
                val type = call.principal<UserPrincipal>()?.type
                val userId = call.principal<UserPrincipal>()?.userId

                val token = PasswordHasher.hashPassword(userName)

                tokenList[token] = UserInfo(userName, type ?: UserType.None, userId!!)

                call.sessions.set(UserSession(token = token))

                when (type) {
                    UserType.Student -> call.respondRedirect("/student")
                    UserType.Secretary -> call.respondRedirect("/secretary")
                    else -> {call.respondRedirect("/logout")}
                }
                call.respondText("Hello, ${call.principal<UserPrincipal>()?.userName}!")
            }
        }
        authenticate("auth-session") {
            get("/secretary") {
                val token = call.sessions.get<UserSession>()?.token
                if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Secretary)) {
                    val secretary = secretaryController.getById(tokenList[token]?.userId!!)!!
                    call.respond(VelocityContent("templates/secretary.vm", mapOf("secretary" to secretary)))
                }else {
                    call.respondRedirect("/logout")
                }
            }
            get("/student") {
                val token = call.sessions.get<UserSession>()?.token
                if (tokenList.containsKey(token) && (tokenList[token]?.type == UserType.Student)) {
                    val student = studentController.getById(tokenList[token]?.userId!!)!!
                    call.respond(VelocityContent("templates/students.vm", mapOf("student" to student)))
                }else {
                    call.respondRedirect("/logout")
                }
            }
        }
        get("/login"){
            call.respond(VelocityContent("templates/login.vm", mapOf()))
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
        route("Test") {
            val studentsController = StudentController()
            get {
                studentsController.add(Student(0, "Short", "Lived", "Man", "No", "email"))
            }
        }
        static("assets") {
            staticRootFolder = File("src/main/resources/assets")
            files(".")
        }
    }
}