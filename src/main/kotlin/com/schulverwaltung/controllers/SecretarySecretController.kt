package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ISecretController
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.UnitOfWork
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class SecretarySecretController : ISecretController {
    private val _unitOfWork = UnitOfWork()
    override suspend fun getByUserName(userName: String): ISecret? {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.getByUserName(userName)
        }
    }

    override suspend fun getAll(): List<ISecret> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.getAll()
        }    }

    override suspend fun getById(id: Int): ISecret? {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.getById(id)
        }
    }

    override suspend fun add(entity: ISecret): ISecret {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.add(entity)
        }
    }

    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            return@newSuspendedTransaction _unitOfWork.secretarySecretRepository.add(entities)
        }
    }

    override suspend fun update(entity: ISecret): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.update(entity)
        }    }

    override suspend fun delete(entity: ISecret): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.delete(entity)
        }    }

    override suspend fun deleteById(id: Int): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretarySecretRepository.deleteById(id)
        }    }

    override suspend fun deleteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val result = _unitOfWork.secretarySecretRepository.deleteAll()
            _unitOfWork.secretarySecretRepository.resetAutoIncrement(this)
            return@newSuspendedTransaction result
        }    }

}