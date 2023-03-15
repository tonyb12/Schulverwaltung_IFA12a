package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.CsvImportHistories
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.repository.interfaces.ICsvImportHistoryRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CSVImportHistoryRepository : ICsvImportHistoryRepository {
    override suspend fun getLatest(): CSVImportHistory {
        val csvImportHistory = CsvImportHistories.selectAll().orderBy(CsvImportHistories.id,SortOrder.DESC).limit(1).single()

        return CSVImportHistory.fromRow(csvImportHistory)
    }

    override fun getAll(): List<CSVImportHistory> {
        return CsvImportHistories.selectAll().map { CSVImportHistory.fromRow(it) }
    }

    override fun getById(id: Int): CSVImportHistory? {
        val csvImportHistory = CsvImportHistories.select{ CsvImportHistories.id eq id}.singleOrNull() ?: return null
        return CSVImportHistory.fromRow(csvImportHistory)
    }

    override fun add(entity: CSVImportHistory): CSVImportHistory {
        val id = CsvImportHistories.insert {
            it[id] = entity.id
            it[uploadTime] = entity.uploadTime
            it[fileName] = entity.fileName
        } get CsvImportHistories.id
        return CSVImportHistory.fromRow(CsvImportHistories.select { CsvImportHistories.id eq id }.single())
    }

    override fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        return CsvImportHistories.batchInsert(entities){
            this[CsvImportHistories.id] = it.id
            this[CsvImportHistories.uploadTime] = it.uploadTime
        }.toList().map{ CSVImportHistory.fromRow(it)}
    }

    override fun update(entity: CSVImportHistory): Int {
        return CsvImportHistories.update {
            it[id] = entity.id
            it[uploadTime] = entity.uploadTime
        }
    }

    override fun delete(entity: CSVImportHistory): Int {
        return CsvImportHistories.deleteAll()
    }

    override fun deleteById(id: Int): Int {
        return CsvImportHistories.deleteWhere{ CsvImportHistories.id eq id}
    }

    override fun deleteAll(): Int {
        return CsvImportHistories.deleteAll()
    }

    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE CSVImportHistories AUTO_INCREMENT = 1")
    }
}