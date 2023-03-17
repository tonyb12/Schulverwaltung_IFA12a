package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.ICsvHistoryController
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

/**
 * CSVImportHistoryController acts as a middleware between the caller and the repository
 * Holds main business logic
 *
 * @property _unitOfWork
 */
class CSVImportHistoryController(
    private val _unitOfWork: IUnitOfWork
) : ICsvHistoryController {
    /**
     * Gets the latest CSVImportHistory
     *
     * @return CSVImportHistory or null
     */
    override suspend fun getLatest(): CSVImportHistory? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getLatest()
        }
    }
    /**
     * Get a list of CSVImportHistories
     *
     * @return List of CSVImportHistory
     */
    override suspend fun getAll(): List<CSVImportHistory> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getAll()
        }
    }
    /**
     * Gets a CSVImportHistory by its id
     *
     * @param id of the CSVImportHistory entry
     * @return CSVImportHistory or null
     */
    override suspend fun getById(id: Int): CSVImportHistory? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getById(id)
        }
    }
    /**
     * Adds a CSVImportHistory to the csv import history repository
     *
     * @param entity CSVImportHistory
     * @return CSVImportHistory
     */
    override suspend fun add(entity: CSVImportHistory): CSVImportHistory {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.add(entity)
        }
    }
    /**
     * Adds a list of CSVImportHistory to the csv import history repository
     *
     * @param entities List of CSVImportHistory
     * @return List of CSVImportHistory
     */
    override suspend fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.add(entities)
        }
    }
    /**
     * Updates a CSVImportHistory entry in the csv import history repository
     *
     * @param entity CSVImportHistory
     * @return 1 if change was successful otherwise 0
     */
    override suspend fun update(entity: CSVImportHistory): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.update(entity)
        }
    }
    /**
     * Deletes a CSVImportHistory entry from the csv import history repository
     *
     * @param entity CSVImportHistory
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun delete(entity: CSVImportHistory): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {

            _unitOfWork.csvImportHistoryRepository.delete(entity)
        }
    }
    /**
     * Deletes a CSVImportHistory by its id
     *
     * @param id id of the CSVImportHistory entry
     * @return 1 if deletion was successful otherwise 0
     */
    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.deleteById(id)
        }
    }
    /**
     * Deletes all entries from the csv import history repository + resets the auto increment
     *
     * @return An integer representing the number of deletions
     */
    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.csvImportHistoryRepository.deleteAll()
            _unitOfWork.csvImportHistoryRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}