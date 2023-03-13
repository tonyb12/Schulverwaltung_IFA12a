package com.example.controllers

import com.example.controllers.interfaces.ISecretaryController
import com.example.database.exposed.ExposedDb
import com.example.dto.Secretary
import com.example.dto.SecretarySecret
import com.example.unitofwork.UnitOfWork
import com.example.utils.PasswordHasher
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
             _unitOfWork.secretaryRepository.deleteAll()
        }
    }
}