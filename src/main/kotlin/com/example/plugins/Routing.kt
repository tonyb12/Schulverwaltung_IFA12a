package com.example.plugins

import com.example.database.DAOFacadeImpl
import com.example.model.Student
import com.example.model.Student.Students.id
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
        get("/students/{id}"){
            val dao = DAOFacadeImpl()
            val id = call.parameters["id"]
            if (id != null) {
                val student: Student? = dao.getStudent(id.toInt())
                student ?.let {
                    call.respond(VelocityContent("templates/index.vl", mapOf("student" to student.surname)));
                }
            } else {

            }

        }
    }
}
