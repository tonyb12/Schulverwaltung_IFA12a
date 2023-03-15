package com.schulverwaltung.unitofwork.interfaces

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import com.schulverwaltung.repository.interfaces.ISecretarySecretRepository
import com.schulverwaltung.repository.interfaces.IStudentRepository
import com.schulverwaltung.repository.interfaces.IStudentSecretRepository
import org.jetbrains.exposed.sql.Database

interface IUnitOfWork {
    val databaseConnection: Database
    val transactionMiddleware: ITransactionMiddleware
    val secretaryRepository: ISecretaryRepository
    val studentRepository: IStudentRepository
    val secretarySecretRepository: ISecretarySecretRepository
    val studentSecretRepository: IStudentSecretRepository

    fun commit()
    fun rollback()
}