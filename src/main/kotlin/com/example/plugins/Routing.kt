package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.velocity.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/user"){

            val user = Student()
            call.respond(VelocityContent("templates/index.vl", mapOf("user" to user)));
        }
    }
}
