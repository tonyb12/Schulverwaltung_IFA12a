package com.example.plugins

import com.example.database.DAOFacadeImpl
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
            call.respond(VelocityContent("templates/login.vm", mapOf()))
        }
        get("/students/{id}"){
            val dao = DAOFacadeImpl()
            val id = call.parameters["id"]
            if (id != null) {
                val student: Student? = dao.getStudent(id.toInt())
                student ?.let {
                    call.respond(VelocityContent("templates/students.vm", mapOf("student" to student)));
                }
            } else {

            }
        }
        static("assets") {
            staticRootFolder = File("src/main/resources/assets")
            files(".")
        }
    }
}
