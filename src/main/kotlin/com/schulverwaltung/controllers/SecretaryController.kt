package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ISecretaryController
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IPasswordHasher

class SecretaryController(
    private val _unitOfWork: IUnitOfWork,
    private val _passwordHasher: IPasswordHasher
) : ISecretaryController {
    override suspend fun getAll(): List<Secretary> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): Secretary? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.getById(id)
        }
    }

    override suspend fun add(entity: Secretary): Secretary {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val secretary = _unitOfWork.secretaryRepository.add(entity)
            _unitOfWork.secretarySecretRepository.add(
                SecretarySecret(
                    0,
                    entity.firstName,
                    _passwordHasher.hashPassword(entity.firstName),
                    secretary.id
                )
            )
            return@newTransactionScope secretary
        }
    }

    override suspend fun add(entities: List<Secretary>): List<Secretary> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.add(entities)
        }
    }

    override suspend fun update(entity: Secretary): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.update(entity)
        }
    }

    override suspend fun delete(entity: Secretary): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.secretaryRepository.deleteAll()
            _unitOfWork.secretaryRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}