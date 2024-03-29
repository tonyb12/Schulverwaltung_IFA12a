package com.schulverwaltung.unitofwork.interfaces

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.*
import org.jetbrains.exposed.sql.Database

/**
 * UnitOfWork
 * Can be used to bundle database operations into units that can collectively be committed or rollbacked
 *
 * @property transactionMiddleware
 * @property secretaryRepository
 * @property studentRepository
 * @property secretarySecretRepository
 * @property studentSecretRepository
 * @property csvImportHistoryRepository
 */
interface IUnitOfWork {
    val databaseConnection: Database
    val transactionMiddleware: ITransactionMiddleware
    val secretaryRepository: ISecretaryRepository
    val studentRepository: IStudentRepository
    val secretarySecretRepository: ISecretarySecretRepository
    val studentSecretRepository: IStudentSecretRepository
    val csvImportHistoryRepository: ICsvImportHistoryRepository

    /**
     * Commit all operations of the current transaction to the database
     *
     */
    fun commit()

    /**
     * Rollback all operations of the current transaction
     *
     */
    fun rollback()
}