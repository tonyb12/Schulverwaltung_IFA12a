package com.schulverwaltung.unitofwork

import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import com.schulverwaltung.repository.interfaces.ISecretarySecretRepository
import com.schulverwaltung.repository.interfaces.IStudentRepository
import com.schulverwaltung.repository.interfaces.IStudentSecretRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

class UnitOfWork(
    private val _transactionMiddleware: ITransactionMiddleware,
    private var _secretaryRepository: ISecretaryRepository,
    private var _studentRepository: IStudentRepository,
    private val _secretarySecretRepository: ISecretarySecretRepository,
    private val _studentSecretRepository: IStudentSecretRepository
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

    override fun commit() {
        TransactionManager.current().commit()
    }

    override fun rollback() {
        TransactionManager.current().rollback()
    }
}