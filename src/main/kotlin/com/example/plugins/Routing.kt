package com.example.plugins

import com.example.database.dao
import com.example.model.Student.Students.id
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.velocity.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/students"){

            call.respond(VelocityContent("templates/index.vl", mapOf("student" to dao.getAllStudents())));
        }

       // get("/student?{$id}"){

        //    val id = call.parameters["id"]?.toInt()
        //    val student = students.find { it.id == id }
        //    call.respond(VelocityContent("templates/index.vl", mapOf("student" to dao.getStudent(call.respond(student)))))
       // }
    }
}
