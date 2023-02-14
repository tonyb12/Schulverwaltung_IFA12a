package com.example.plugins

import com.example.model.Student
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.velocity.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/user"){
            val user = Student(1, "Bui", "Toan","IFA12A", "01.01.1970","test@test.de")
            call.respond(VelocityContent("templates/users.vm", mapOf("user" to user)))
        }
        static("assets") {
            staticRootFolder = File("src/main/resources/assets")
            files(".")
        }
    }
}
