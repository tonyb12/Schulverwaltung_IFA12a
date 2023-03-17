package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.IStudentSecretController
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork


open class StudentSecretController(private val _unitOfWork: IUnitOfWork) : IStudentSecretController {
    override suspend fun getByUserName(userName: String): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getByUserName(userName)
        }
    }

    override suspend fun getAll(): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getById(id)
        }
    }

    override suspend fun add(entity: ISecret): ISecret {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.add(entity)
        }
    }

    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.add(entities)
        }
    }

    override suspend fun update(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.update(entity)
        }
    }

    override suspend fun delete(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.studentSecretRepository.deleteAll()
            _unitOfWork.studentSecretRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}