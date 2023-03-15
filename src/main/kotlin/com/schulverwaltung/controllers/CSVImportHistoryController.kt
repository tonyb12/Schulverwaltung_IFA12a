package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ICsvHistoryController
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

class CSVImportHistoryController(
    private val _unitOfWork: IUnitOfWork
) : ICsvHistoryController {
    override suspend fun getLatest(): CSVImportHistory? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getLatest()
        }
    }

    override suspend fun getAll(): List<CSVImportHistory> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): CSVImportHistory? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.getById(id)
        }
    }

    override suspend fun add(entity: CSVImportHistory): CSVImportHistory {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.add(entity)
        }
    }

    override suspend fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.add(entities)
        }
    }

    override suspend fun update(entity: CSVImportHistory): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.update(entity)
        }
    }

    override suspend fun delete(entity: CSVImportHistory): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {

            _unitOfWork.csvImportHistoryRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.csvImportHistoryRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.csvImportHistoryRepository.deleteAll()
            _unitOfWork.csvImportHistoryRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }


}