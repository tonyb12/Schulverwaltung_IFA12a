package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.ISecretarySecretController
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

/**
 * SecretarySecretController acts as a middleware between the caller and the repository
 * Holds main business logic
 *
 * @property _unitOfWork
 */
open class SecretarySecretController(private val _unitOfWork: IUnitOfWork) : ISecretarySecretController {
    /**
     * Gets a SecretarySecret by its username
     *
     * @param userName Name of the user
     * @return SecretarySecret that fulfills the contract ISecret or null
     */
    override suspend fun getByUserName(userName: String): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getByUserName(userName)
        }
    }
    /**
     * Get a list of SecretarySecrets
     *
     * @return List of SecretarySecret that all fulfill the contract ISecret
     */
    override suspend fun getAll(): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getAll()
        }
    }

    /**
     * Gets a secretary secret by its id
     *
     * @param id of the SecretarySecret entry
     * @return SecretarySecret that fulfills the contract ISecret or null
     */
    override suspend fun getById(id: Int): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.getById(id)
        }
    }
    /**
     * Adds a SecretarySecret to the secretary secret repository
     *
     * @param entity SecretarySecret that fulfills the contract ISecret
     * @return the SecretarySecret that has been added to the secretary secret repository that fulfills the contract ISecret
     */
    override suspend fun add(entity: ISecret): ISecret {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.add(entity)
        }
    }
    /**
     * Adds a list of SecretarySecret to the secretary secret repository
     *
     * @param entities a list of SecretarySecret that all fulfills the contract ISecret
     * @return List of SecretarySecret that have been added to the secretary secret repository that all fulfill the contract ISecret
     */
    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            return@newTransactionScope _unitOfWork.secretarySecretRepository.add(entities)
        }
    }
    /**
     * Updates a SecretarySecret entry in the secretary secret repository
     *
     * @param entity SecretarySecret that fulfills the contract ISecret
     * @return 1 if change was successful otherwise 0
     */
    override suspend fun update(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.update(entity)
        }
    }
    /**
     * Deletes a SecretarySecret entry from the secretary secret repository
     *
     * @param entity SecretarySecret that fulfills the contract ISecret
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun delete(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.delete(entity)
        }
    }
    /**
     * Deletes a SecretarySecret by its id
     *
     * @param id id of the SecretarySecret entry
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.secretarySecretRepository.deleteById(id)
        }
    }
    /**
     * Deletes all entries from the secretary secret repository + resets the auto increment
     *
     * @return An integer representing the number of deletions
     */
    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.secretarySecretRepository.deleteAll()
            _unitOfWork.secretarySecretRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }

}