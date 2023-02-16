package com.example.unitofwork

import com.example.database.exposed.ExposedDb
import com.example.repository.SecretaryRepository
import com.example.repository.SecretarySecretRepository
import com.example.repository.StudentRepository
import com.example.repository.interfaces.ISecretaryRepository
import com.example.repository.interfaces.ISecretRepository
import com.example.repository.interfaces.IStudentRepository
import com.example.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

class UnitOfWork : IUnitOfWork {
    val databaseConnection: Database
    private var _secretaryRepository: ISecretaryRepository? = null
    private var _studentRepository: IStudentRepository? = null
    private var _secretarySecretRepository: ISecretRepository? = null
    private var _studentSecretRepository: ISecretRepository? = null

    constructor() {
        this.databaseConnection = ExposedDb.connection
    }
    constructor(databaseConnection: Database) {
        this.databaseConnection = databaseConnection
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
                _studentSecretRepository = SecretarySecretRepository()
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