package com.example

import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.velocity.*
import io.ktor.websocket.*
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
}
