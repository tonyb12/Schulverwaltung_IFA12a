package com.example

import com.example.database.DAOFacade
import com.example.database.DAOFacadeImpl
import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.velocity.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader

fun main() {

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()

    }.start(wait = true)
}

fun Application.module() {
    configureRouting();
    DatabaseFactory.init();
    install(Velocity) {
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        setProperty("classpath.resource.loader.class", ClasspathResourceLoader::class.java.name);
    }

    DAOFacadeImpl().apply {
        runBlocking {
            if(getAllStudents().isEmpty()){
                addStudents(1,"Bui","Toan","IFA12a","12.12.1998", "tony.bui267@gmail.com")
            }
        }
    }
}
