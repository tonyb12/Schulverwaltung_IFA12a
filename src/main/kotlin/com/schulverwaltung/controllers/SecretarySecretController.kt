package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ISecretarySecretController
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

class SecretarySecretController(private val _unitOfWork: IUnitOfWork) : ISecretarySecretController {
    override suspend fun getByUserName(userName: String): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getByUserName(userName)
        }
    }

    override suspend fun getAll(): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getById(id)
        }
    }

    override suspend fun add(entity: ISecret): ISecret {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.add(entity)
        }
    }

    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            return@newTransactionScope _unitOfWork.secretarySecretRepository.add(entities)
        }
    }

    override suspend fun update(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.update(entity)
        }
    }

    override suspend fun delete(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.secretarySecretRepository.deleteAll()
            _unitOfWork.secretarySecretRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }

}