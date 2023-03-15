package com.schulverwaltung.unitofwork.interfaces

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.*
import org.jetbrains.exposed.sql.Database

interface IUnitOfWork {
    val databaseConnection: Database
    val transactionMiddleware: ITransactionMiddleware
    val secretaryRepository: ISecretaryRepository
    val studentRepository: IStudentRepository
    val secretarySecretRepository: ISecretarySecretRepository
    val studentSecretRepository: IStudentSecretRepository
    val csvImportHistoryRepository: ICsvImportHistoryRepository

    fun commit()
    fun rollback()
}