package com.example

import com.example.data.CSVTest
import com.example.database.DAOFacade
import com.example.database.DAOFacadeImpl
import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.velocity.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.charset.Charset

fun main() {

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting();
        module()
    }.start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init();
    val studentsFacade = DAOFacadeImpl()

    if (false) {
        val csvTest = CSVTest()
        val filePath = ""
        val file = File(filePath)
        val inputStream = file.inputStream()
        val parsedData = csvTest.readCsv(inputStream)

        launch {
            studentsFacade.apply {
                parsedData.map {
                    val addedStudent = async { addStudent(it.surname, it.lastname, it.class_name, it.birthday, it.email) }
                    delay(10000)
                    System.out.println(addedStudent.await())
                }
            }
        }
    }

    install(Velocity) {
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        setProperty("classpath.resource.loader.class", ClasspathResourceLoader::class.java.name);
    }
}
