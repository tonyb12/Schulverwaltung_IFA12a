package com.schulverwaltung.controller

import com.schulverwaltung.MultiException
import com.schulverwaltung.controller.interfaces.ISecretaryController
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import java.time.format.DateTimeParseException

open class SecretaryController(
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
    /**
     * Adds a secretary to the repository and creates a student secret. If an error occurs a rollback happens otherwise a commit to the database
     *
     * @param entity as a student
     * @return a secretary that has been added to the database
     */
    override suspend fun add(entity: Secretary): Secretary {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            try {
                val secretary = _unitOfWork.secretaryRepository.add(entity)

                _unitOfWork.secretarySecretRepository.add(
                    SecretarySecret(
                        0,
                        entity.firstName,
                        _passwordHasher.hashPassword(entity.firstName),
                        secretary.id
                    )
                )
                _unitOfWork.commit()
                return@newTransactionScope secretary
            }catch (e: Exception) {
                _unitOfWork.rollback()
                throw e
            }
        }
    }
    /**
     * Adds a list of students to the repository and creates their secrets. If an exception occurs, it will be cached.
     * At the end all students where an error occurred are removed from the repository -> Partial rollback
     *
     * @param entities
     * @return a list of students
     * @throws MultiException
     */
    @Throws(MultiException::class)
    override suspend fun add(entities: List<Secretary>): List<Secretary> {
        val result = _unitOfWork.transactionMiddleware.newAsyncTransactionScope {
            if (entities.isEmpty()){
                return@newAsyncTransactionScope listOf<Secretary>()
            }
            val errorList = mutableListOf<Exception>()
            val failedEntities = mutableListOf<Int>()
            val secretaries = _unitOfWork.secretaryRepository.add(entities)
            val secretList = mutableListOf<ISecret>()
            secretaries.parallelStream().forEach {
                try {
                    secretList.add(
                        SecretarySecret(
                            0,
                            it.firstName,
                            _passwordHasher.hashPassword(it.firstName),
                            it.id
                        )
                    )
                }catch (e: Exception) {
                    failedEntities.add(it.id)
                    errorList.add(e)
                }
            }
            _unitOfWork.secretarySecretRepository.add(secretList)
            if (failedEntities.isNotEmpty()){
                failedEntities.forEach {
                    _unitOfWork.secretaryRepository.deleteById(it)
                }
                throw MultiException(errorList)
            }
            _unitOfWork.commit()
            return@newAsyncTransactionScope secretaries
        }
        return result.await()
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