package com.example.controllers

import com.example.controllers.interfaces.ISecretController
import com.example.dto.interfaces.ISecret
import com.example.unitofwork.UnitOfWork
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class StudentsSecretController : ISecretController {
    private val _unitOfWork = UnitOfWork()

    override suspend fun getByUserName(userName: String): ISecret? {
        return newSuspendedTransaction(Dispatchers.IO, _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.getByUserName(userName)
        }
    }

    override suspend fun getAll(): List<ISecret> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): ISecret? {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.getById(id)
        }
    }

    override suspend fun add(entity: ISecret): ISecret {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.add(entity)
        }
    }

    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.add(entities)
        }
    }

    override suspend fun update(entity: ISecret): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.update(entity)
        }
    }

    override suspend fun delete(entity: ISecret): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentSecretRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val result = _unitOfWork.studentSecretRepository.deleteAll()
            _unitOfWork.studentSecretRepository.resetAutoIncrement(this)
            return@newSuspendedTransaction result
        }
    }
}