package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.ICsvHistoryController
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.unitofwork.UnitOfWork
import com.schulverwaltung.utils.PasswordHasher
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CSVImportHistoryController : ICsvHistoryController {

    private  val _unitOfWork: UnitOfWork = UnitOfWork()
    override suspend fun getLatest(): CSVImportHistory {

        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){

            _unitOfWork.csvImportHistoryRepository.getLatest()
        }
    }

    override suspend fun getAll(): List<CSVImportHistory> {

        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){

            _unitOfWork.csvImportHistoryRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): CSVImportHistory? {
        return  newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){
            _unitOfWork.csvImportHistoryRepository.getById(id)
        }
    }

    override suspend fun add(entity: CSVImportHistory): CSVImportHistory {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){
            val csvImportHistory = _unitOfWork.csvImportHistoryRepository.add(entity)
            _unitOfWork.csvImportHistoryRepository.add(CSVImportHistory(0,entity.uploadTime, entity.fileName))
            return@newSuspendedTransaction csvImportHistory
        }

    }

    override suspend fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        return newSuspendedTransaction(Dispatchers.IO, _unitOfWork.databaseConnection){
            _unitOfWork.csvImportHistoryRepository.add(entities)
        }
    }

    override suspend fun update(entity: CSVImportHistory): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){
            _unitOfWork.csvImportHistoryRepository.update(entity)
        }
    }

    override suspend fun delete(entity: CSVImportHistory): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){

            _unitOfWork.csvImportHistoryRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){
            _unitOfWork.csvImportHistoryRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection){
            val result = _unitOfWork.csvImportHistoryRepository.deleteAll()
            _unitOfWork.csvImportHistoryRepository.resetAutoIncrement(this)
            return@newSuspendedTransaction result
        }
    }


}