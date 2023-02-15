package com.example.unitofwork

import com.example.database.exposed.ExposedDb
import com.example.repository.SecretaryRepository
import com.example.repository.StudentRepository
import com.example.repository.interfaces.ISecretaryRepository
import com.example.repository.interfaces.IStudentRepository
import org.jetbrains.exposed.sql.Database

class UnitOfWork {
    private val _databaseConnection: Database = ExposedDb.connection
    private var _secretaryRepository: ISecretaryRepository? = null
    private var _studentRepository: IStudentRepository? = null
    val secretaryRepository: ISecretaryRepository
        get(): ISecretaryRepository {
            if (_secretaryRepository == null) {
                _secretaryRepository = SecretaryRepository(_databaseConnection)
            }
            return _secretaryRepository!!
        }

    val studentRepository: IStudentRepository
        get(): IStudentRepository {
            if (_studentRepository == null) {
                _studentRepository = StudentRepository(_databaseConnection)
            }
            return _studentRepository!!
        }
}