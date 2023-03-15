package com.schulverwaltung


import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.database.objects.*
import com.schulverwaltung.dto.CSVImportHistory

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.schulverwaltung.plugins.*
import io.ktor.server.velocity.*

import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.app.event.implement.IncludeRelativePath
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        module()


    }.start(wait = true)
}

fun Application.module() {
    install(Velocity) {
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath")
        setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath::class.java.name)
        setProperty("classpath.resource.loader.class", ClasspathResourceLoader::class.java.name)
        setProperty("input.encoding", "UTF-8")
        setProperty("output.encoding", "UTF-8")
    }
}
fun Application.initDb() {
}
fun Application.database() {
    transaction(ExposedDb.connection) {
        SchemaUtils.create(Students, Secretaries, SecretarySecrets, StudentSecrets, CsvImportHistories)
    }
}
