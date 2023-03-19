package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.ISecretaryController
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.exceptions.MultiException
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IPasswordHasher

/**
 * SecretaryController acts as a middleware between the caller and the repository
 * Holds main business logic
 *
 * @property _unitOfWork
 * @property _passwordHasher
 */
open class SecretaryController(
    private val _unitOfWork: IUnitOfWork,
    private val _passwordHasher: IPasswordHasher
) : ISecretaryController {
    /**
     * Get a list of Secretaries
     *
     * @return List of Secretaries
     */
    override suspend fun getAll(): List<Secretary> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.getAll()
        }
    }
    /**
     * Gets a Secretary by its id
     *
     * @param id of the Secretary entry
     * @return Secretary or null
     */
    override suspend fun getById(id: Int): Secretary? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.getById(id)
        }
    }

    /**
     * Adds a Secretary to the secretary repository and creates a secretary secret in the secretary secret repository.
     * If an error occurs a rollback happens otherwise a commit to the database
     *
     * @param entity Secretary
     * @return the Secretary that has been added to the secretary repository
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
            } catch (e: Exception) {
                _unitOfWork.rollback()
                throw e
            }
        }
    }

    /**
     * Adds a list of secretaries to the secretary repository and creates their secrets in the secretary secret repository. If an exception occurs, it will be cached.
     * At the end all secretaries where an error occurred are removed from the secretary repository -> Partial rollback
     *
     * @param entities List of Secretaries
     * @return List of Secretaries that have been added to the repository
     * @throws MultiException
     */
    @Throws(MultiException::class)
    override suspend fun add(entities: List<Secretary>): List<Secretary> {
        val result = _unitOfWork.transactionMiddleware.newAsyncTransactionScope {
            if (entities.isEmpty()) {
                return@newAsyncTransactionScope listOf<Secretary>()
            }
            val errorList = mutableListOf<Exception>()
            val failedEntities = mutableListOf<Int>()
            val secretaries = _unitOfWork.secretaryRepository.add(entities)
            val secretList = mutableListOf<ISecret>()
            secretaries.forEach {
                try {
                    secretList.add(
                        SecretarySecret(
                            0,
                            it.firstName,
                            _passwordHasher.hashPassword(it.firstName),
                            it.id
                        )
                    )
                } catch (e: Exception) {
                    failedEntities.add(it.id)
                    errorList.add(e)
                }
            }
            _unitOfWork.secretarySecretRepository.add(secretList)

            if (failedEntities.isNotEmpty()) {
                failedEntities.forEach {
                    _unitOfWork.secretaryRepository.deleteById(it)
                }
            }

            _unitOfWork.commit()

            if (errorList.isNotEmpty()) {
                throw MultiException(errorList)
            }
            return@newAsyncTransactionScope secretaries
        }
        return result.await()
    }
    /**
     * Updates a Secretary entry in the secretary repository
     *
     * @param entity Secretary
     * @return 1 if change was successful otherwise 0
     */
    override suspend fun update(entity: Secretary): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.update(entity)
        }
    }
    /**
     * Deletes a Secretary entry from the secretary repository
     *
     * @param entity Secretary
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun delete(entity: Secretary): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.delete(entity)
        }
    }
    /**
     * Deletes a Secretary by its id
     *
     * @param id
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretaryRepository.deleteById(id)
        }
    }
    /**
     * Deletes all entries from the secretary repository + resets the auto increment
     *
     * @return An integer representing the number of deletions
     */
    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.secretaryRepository.deleteAll()
            _unitOfWork.secretaryRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}