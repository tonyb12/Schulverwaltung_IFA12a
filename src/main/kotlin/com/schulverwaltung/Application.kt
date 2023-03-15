package com.schulverwaltung


import com.schulverwaltung.controllers.*
import com.schulverwaltung.controllers.interfaces.*
import com.schulverwaltung.database.ExposedTransactionMiddleware
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.plugins.configureRouting
import com.schulverwaltung.repository.*
import com.schulverwaltung.repository.interfaces.*
import com.schulverwaltung.unitofwork.UnitOfWork
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.PasswordHasher
import com.schulverwaltung.utils.UserNameGenerator
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.velocity.*
import org.apache.velocity.app.event.implement.IncludeRelativePath
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val prodDIModule = module {
    single<IPasswordHasher> { PasswordHasher() }
    single<ITransactionMiddleware> { ExposedTransactionMiddleware() }
    single<IBirthdayParser> { BirthdayParser() }
    singleOf(::UserNameGenerator) bind IUserNameGenerator::class
    singleOf(::StudentRepository) bind IStudentRepository::class
    singleOf(::StudentsSecretRepository) bind IStudentSecretRepository::class
    singleOf(::SecretaryRepository) bind ISecretaryRepository::class
    singleOf(::SecretarySecretRepository) bind ISecretarySecretRepository::class
    singleOf(::CSVImportHistoryRepository) bind ICsvImportHistoryRepository::class
    singleOf(::UnitOfWork) bind IUnitOfWork::class
    singleOf(::StudentController) bind IStudentController::class
    singleOf(::StudentsSecretController) bind IStudentSecretController::class
    singleOf(::SecretaryController) bind ISecretaryController::class
    singleOf(::SecretarySecretController) bind ISecretarySecretController::class
    singleOf(::CSVImportHistoryController) bind ICsvHistoryController::class
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
        configureRouting()
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
    install(Koin) {
        modules(prodDIModule)
    }
}