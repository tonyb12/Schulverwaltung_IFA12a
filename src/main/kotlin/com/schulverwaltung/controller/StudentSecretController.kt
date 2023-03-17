package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.IStudentSecretController
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.exceptions.MultiException
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork


/**
 * StudentSecretController acts as a middleware between the caller and the repository
 * Holds main business logic
 *
 * @property _unitOfWork
 */
open class StudentSecretController(private val _unitOfWork: IUnitOfWork) : IStudentSecretController {
    /**
     * Gets a StudentSecret by its username
     *
     * @return StudentSecret that fulfills the contract ISecret or null
     */
    override suspend fun getByUserName(userName: String): ISecret? {

        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getByUserName(userName)
        }
    }
    /**
     * Get a list of StudentSecret
     *
     * @return List of StudentSecret that all fulfill the contract ISecret
     */
    override suspend fun getAll(): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getAll()
        }
    }
    /**
     * Gets a StudentSecret by its id
     *
     * @param id of the StudentSecret entry
     * @return StudentSecret that fulfills the contract ISecret or null
     */
    override suspend fun getById(id: Int): ISecret? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.getById(id)
        }
    }
    /**
     * Adds a StudentSecret to the student secret repository
     *
     * @param entity StudentSecret that fulfills the contract ISecret
     * @return the StudentSecret that has been added to the student secret repository that fulfills the contract ISecret
     */
    override suspend fun add(entity: ISecret): ISecret {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.add(entity)
        }
    }

    /**
     * Adds a list of StudentSecrets to the student secret repository
     *
     * @param entities a list of StudentSecret that all fulfills the contract ISecret
     * @return  List of StudentSecrets that have been added to the student secret repository that all fulfill the contract ISecret
     */
    override suspend fun add(entities: List<ISecret>): List<ISecret> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.add(entities)
        }
    }
    /**
     * Updates a StudentSecret entry in the student secret repository
     *
     * @param entity StudentSecret that fulfills the contract ISecret
     * @return 1 if change was successful otherwise 0
     */
    override suspend fun update(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.update(entity)
        }
    }
    /**
     * Deletes a StudentSecret entry from the student secret repository
     *
     * @param entity StudentSecret that fulfills the contract ISecret
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun delete(entity: ISecret): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.delete(entity)
        }
    }
    /**
     * Deletes a StudentSecret by its id
     *
     * @param id of the StudentSecret entry
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentSecretRepository.deleteById(id)
        }
    }
    /**
     * Deletes all entries from the student secret repository + resets the auto increment
     *
     * @return An integer representing the number of deletions
     */
    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.studentSecretRepository.deleteAll()
            _unitOfWork.studentSecretRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}