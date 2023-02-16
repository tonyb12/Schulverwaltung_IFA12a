package com.example.plugins

import com.example.controllers.SecretarySecretController
import com.example.controllers.StudentController
import com.example.controllers.StudentsSecretController
import com.example.dto.Secret
import com.example.dto.Student
import com.example.validator.CredentialValidator
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.Authentication
import io.ktor.server.http.content.*
import io.ktor.server.sessions.*
import io.ktor.server.velocity.*
import java.io.File

data class UserSession(val name: String) : Principal

fun Application.configureRouting() {
    val secretaryCredentialValidator = CredentialValidator(SecretarySecretController())
    val studentsCredentalValidator = CredentialValidator(StudentsSecretController())

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
                if (secretaryCredentialValidator.verifyCredentials(credentials.name, credentials.password)) {
                    UserIdPrincipal(credentials.name)
                } else null
            }
            challenge {
                print(it)
                call.respond("Wrong creds")
            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                print(session)
                session
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }
    routing {
        get("/addUsers") {
            val controller = SecretarySecretController()
            //controller.add(Secret(0, "Nico", secretaryCredentialValidator.hashPassword("Password")))
            controller.add(Secret(0, "Toan", secretaryCredentialValidator.hashPassword("Bui")))
        }
        authenticate("auth-form", strategy = AuthenticationStrategy.Required) {
            /*
            get("/student") {
                //call.respond(VelocityContent("templates/students.vm", mapOf("student" to student)))
            }*/
            post("/login") {
                val userName = call.principal<UserIdPrincipal>()?.name.toString()
                call.sessions.set(UserSession(name = userName))
                call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
            }
        }
        authenticate("auth-session") {
            get("/secretary") {
                call.respond(VelocityContent("templates/secretary.vm", mapOf()))
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