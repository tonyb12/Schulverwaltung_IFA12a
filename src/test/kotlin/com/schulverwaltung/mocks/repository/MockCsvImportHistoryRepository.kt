package com.schulverwaltung.mocks.repository

import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.repository.interfaces.ICsvImportHistoryRepository
import org.jetbrains.exposed.sql.Transaction

class MockCsvImportHistoryRepository : ICsvImportHistoryRepository {
    private var mockTable = mutableListOf<CSVImportHistory>()
    override fun getLatest(): CSVImportHistory? {
        if (mockTable.isNotEmpty()) {
            mockTable.sortBy { it.id }
            return mockTable.last()
        }
        return null
    }

    override fun getAll(): List<CSVImportHistory> = mockTable

    override fun getById(id: Int): CSVImportHistory? {
        return mockTable.find {
            it.id == id
        }
    }

    override fun add(entity: CSVImportHistory): CSVImportHistory {
        var id = 1
        if (mockTable.isNotEmpty()) {
            mockTable.sortBy { it.id }
            id = mockTable.last().id
            id++
        }
        val tmpCsvImportHistory = CSVImportHistory(id, entity.uploadTime, entity.fileName)
        mockTable.add(tmpCsvImportHistory)
        return tmpCsvImportHistory
    }

    override fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        val newList = mutableListOf<CSVImportHistory>()
        for (entity in entities) {
            newList.add(this.add(entity))
        }
        return newList
    }

    override fun update(entity: CSVImportHistory): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: CSVImportHistory): Int {
        val removed = mockTable.remove(entity)
        return if (removed) 1 else 0
    }

    override fun deleteById(id: Int): Int {
        val removed = mockTable.removeIf { it.id == id }
        return if (removed) 1 else 0
    }

    override fun deleteAll(): Int {
        val length = mockTable.count()
        mockTable.clear()
        return length
    }

    override fun resetAutoIncrement(transaction: Transaction) {
    }
}