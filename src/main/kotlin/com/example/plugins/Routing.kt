package com.example.plugins

import com.example.controllers.StudentController
import com.example.controllers.interfaces.IStudentController
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.velocity.*
import java.io.File

fun Application.configureRouting() {
    routing {
        static("assets") {
            staticRootFolder = File("src/main/resources/assets")
            files(".")
        }
        students(StudentController())
    }
}
fun Routing.students(studentController: IStudentController) {
    route("/students") {
        get {
            studentController.getAll()
        }
        get("/{id}") {
            val idString = call.parameters["id"]
            if (idString == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
                //TODO("Message needed?)
            }
            var id = 0
            try {
                id = idString.toInt()
            } catch (ex: NumberFormatException) {
                call.respond(HttpStatusCode.BadRequest, "Id must be a number")
                return@get
            }
            val student = studentController.getById(id)
            if (student == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(VelocityContent("templates/students.vm", mapOf("student" to student)));

        }
    }
}