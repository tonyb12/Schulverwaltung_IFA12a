package com.example

import com.example.controllers.StudentController
import com.example.data.CSVTest
import com.example.database.exposed.ExposedDb
import com.example.database.objects.Secretaries
import com.example.database.objects.Students
import com.example.model.Student
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.velocity.*
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.app.event.implement.IncludeRelativePath
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting();
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(Velocity) {
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath::class.java.name)
        setProperty("classpath.resource.loader.class", ClasspathResourceLoader::class.java.name);
        setProperty("input.encoding", "UTF-8");
        setProperty("output.encoding", "UTF-8");
    }
}
fun Application.database() {
    transaction(ExposedDb.connection) {
        SchemaUtils.create(Students, Secretaries)
    }
}
fun Application.csv() {
    val studentController = StudentController()

    val csvTest = CSVTest()
    val filePath = "C:\\Users\\nh46\\Downloads\\export.csv"
    val file = File(filePath)
    val inputStream = file.inputStream()
    val parsedData = csvTest.readCsv(inputStream)

    parsedData.map {
        studentController.add(Student(0, it.firstName, it.surName, it.className, it.birthday, it.email))
    }
}