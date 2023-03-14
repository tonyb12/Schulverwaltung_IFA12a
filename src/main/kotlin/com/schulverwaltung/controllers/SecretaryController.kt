package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ISecretaryController
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.unitofwork.UnitOfWork
import com.schulverwaltung.utils.PasswordHasher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class SecretaryController : ISecretaryController{
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override suspend fun getAll(): List<Secretary> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretaryRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): Secretary? {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.secretaryRepository.getById(id)
        }
    }

    override suspend fun add(entity: Secretary): Secretary {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val secretary = _unitOfWork.secretaryRepository.add(entity)
            _unitOfWork.secretarySecretRepository.add(SecretarySecret(0, entity.firstName, PasswordHasher.hashPassword(entity.firstName), secretary.id))
            return@newSuspendedTransaction secretary
        }
    }

    override suspend fun add(entities: List<Secretary>): List<Secretary> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.secretaryRepository.add(entities)
        }
    }

    override suspend fun update(entity: Secretary): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.secretaryRepository.update(entity)
        }
    }

    override suspend fun delete(entity: Secretary): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.secretaryRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.secretaryRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             val result = _unitOfWork.secretaryRepository.deleteAll()
            _unitOfWork.secretaryRepository.resetAutoIncrement(this)
            return@newSuspendedTransaction result
        }
    }
}