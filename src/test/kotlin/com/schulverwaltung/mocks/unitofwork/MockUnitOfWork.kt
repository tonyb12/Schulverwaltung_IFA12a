package com.schulverwaltung.mocks.unitofwork

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.*
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.mockito.Mockito

class MockUnitOfWork(
    private val _transactionMiddleware: ITransactionMiddleware,
    private var _secretaryRepository: ISecretaryRepository,
    private var _studentRepository: IStudentRepository,
    private var _secretarySecretRepository: ISecretarySecretRepository,
    private var _studentSecretRepository: IStudentSecretRepository,
) : IUnitOfWork {
    override val databaseConnection: Database
        get() = Mockito.mock(Database::class.java)
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

    override fun commit() {
    }
    override fun rollback() {
    }
}