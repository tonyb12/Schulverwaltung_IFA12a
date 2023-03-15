package com.schulverwaltung.unitofwork

import com.schulverwaltung.database.ExposedTransactionMiddleware
import com.schulverwaltung.database.ITransactionMiddleware
import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.repository.SecretaryRepository
import com.schulverwaltung.repository.SecretarySecretRepository
import com.schulverwaltung.repository.StudentRepository
import com.schulverwaltung.repository.StudentsSecretRepository
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import com.schulverwaltung.repository.interfaces.ISecretRepository
import com.schulverwaltung.repository.interfaces.IStudentRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager

class UnitOfWork : IUnitOfWork {
    val databaseConnection: Database
    val transactionMiddleware: ITransactionMiddleware<Transaction>
    private var _secretaryRepository: ISecretaryRepository? = null
    private var _studentRepository: IStudentRepository? = null
    private var _secretarySecretRepository: ISecretRepository? = null
    private var _studentSecretRepository: ISecretRepository? = null

    constructor() {
        this.databaseConnection = ExposedDb.connection
        this.transactionMiddleware = ExposedTransactionMiddleware()
    }
    constructor(databaseConnection: Database, transactionMiddleware: ITransactionMiddleware<Transaction>) {
        this.databaseConnection = databaseConnection
        this.transactionMiddleware = transactionMiddleware
    }
    override val secretaryRepository: ISecretaryRepository
        get(): ISecretaryRepository {
            if (_secretaryRepository == null) {
                _secretaryRepository = SecretaryRepository()
            }
            return _secretaryRepository!!
        }

    override val studentRepository: IStudentRepository
        get(): IStudentRepository {
            if (_studentRepository == null) {
                _studentRepository = StudentRepository()
            }
            return _studentRepository!!
        }
    override val secretarySecretRepository: ISecretRepository
        get(): ISecretRepository {
            if (_secretarySecretRepository == null) {
                _secretarySecretRepository = SecretarySecretRepository()
            }
            return _secretarySecretRepository!!
        }
    override val studentSecretRepository: ISecretRepository
        get(): ISecretRepository {
            if (_studentSecretRepository == null) {
                _studentSecretRepository = StudentsSecretRepository()
            }
            return _studentSecretRepository!!
        }

    override fun commit() {
        TransactionManager.current().commit()
    }
    override fun rollback() {
        TransactionManager.current().rollback()
    }
}