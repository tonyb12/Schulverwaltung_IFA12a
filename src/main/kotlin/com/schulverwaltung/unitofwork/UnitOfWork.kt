package com.schulverwaltung.unitofwork

import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.*
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

/**
 * UnitOfWork
 * Can be used to bundle database operations into units that can collectively be committed or rollbacked
 *
 * @property _transactionMiddleware
 * @property _secretaryRepository
 * @property _studentRepository
 * @property _secretarySecretRepository
 * @property _studentSecretRepository
 * @property _csvHistoryImport
 */
class UnitOfWork(
    private val _transactionMiddleware: ITransactionMiddleware,
    private var _secretaryRepository: ISecretaryRepository,
    private var _studentRepository: IStudentRepository,
    private val _secretarySecretRepository: ISecretarySecretRepository,
    private val _studentSecretRepository: IStudentSecretRepository,
    private val _csvHistoryImport: ICsvImportHistoryRepository
) : IUnitOfWork {
    override val databaseConnection: Database
        get() = ExposedDb.connection
    override val transactionMiddleware: ITransactionMiddleware
        get() = _transactionMiddleware

    override val secretaryRepository: ISecretaryRepository
        get() = _secretaryRepository

    override val studentRepository: IStudentRepository
        get() = _studentRepository

    override val secretarySecretRepository: ISecretarySecretRepository
        get() = _secretarySecretRepository

    override val studentSecretRepository: IStudentSecretRepository
        get() = _studentSecretRepository

    override val csvImportHistoryRepository: ICsvImportHistoryRepository
        get() = _csvHistoryImport

    /**
     * Commit all operations of the current transaction to the database
     *
     */
    override fun commit() {
        TransactionManager.current().commit()
    }

    /**
     * Rollback all operations of the current transaction
     *
     */
    override fun rollback() {
        TransactionManager.current().rollback()
    }
}