package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.CsvImportHistories
import com.schulverwaltung.dto.CSVImportHistory
import com.schulverwaltung.repository.interfaces.ICsvImportHistoryRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * CSVImportHistoryRepository acts as an adapter between the controller and database.
 * It contains the logic to access the database and parses the data fetched from the database to an application wide standard
 *
 */
class CSVImportHistoryRepository : ICsvImportHistoryRepository {
    /**
     * Gets the latest entry sorted by the id
     *
     * @return CSVImportHistory or null
     */
    override fun getLatest(): CSVImportHistory? {
        val csvImportHistory =
            CsvImportHistories.selectAll().orderBy(CsvImportHistories.id, SortOrder.DESC).limit(1).singleOrNull()
                ?: return null

        return CSVImportHistory.fromRow(csvImportHistory)
    }
    /**
     * Selects all entries from the database
     *
     * @return List of CSVImportHistories
     */
    override fun getAll(): List<CSVImportHistory> {
        return CsvImportHistories.selectAll().map { CSVImportHistory.fromRow(it) }
    }
    /**
     * Gets an entry by its id
     *
     * @param id of CSVImportHistory
     * @return CSVImportHistory or null
     */
    override fun getById(id: Int): CSVImportHistory? {
        val csvImportHistory = CsvImportHistories.select { CsvImportHistories.id eq id }.singleOrNull() ?: return null
        return CSVImportHistory.fromRow(csvImportHistory)
    }
    /**
     * Inserts an entity into the table
     *
     * @param entity CSVImportHistory
     * @return CSVImportHistory
     */
    override fun add(entity: CSVImportHistory): CSVImportHistory {
        val id = CsvImportHistories.insert {
            it[uploadTime] = entity.uploadTime
            it[fileName] = entity.fileName
        } get CsvImportHistories.id
        return CSVImportHistory.fromRow(CsvImportHistories.select { CsvImportHistories.id eq id }.single())
    }
    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of CSVImportHistory
     * @return List of CSVImportHistories
     */
    override fun add(entities: List<CSVImportHistory>): List<CSVImportHistory> {
        return CsvImportHistories.batchInsert(entities) {
            this[CsvImportHistories.uploadTime] = it.uploadTime
            this[CsvImportHistories.fileName] = it.fileName
        }.toList().map { CSVImportHistory.fromRow(it) }
    }
    /**
     * Updates an entry in the table
     *
     * @param entity CSVImportHistory
     * @return 1 if change was successful otherwise 0
     */
    override fun update(entity: CSVImportHistory): Int {
        return CsvImportHistories.update {
            it[id] = entity.id
            it[uploadTime] = entity.uploadTime
        }
    }
    /**
     * Removes an entry from the table
     *
     * @param entity CSVImportHistory
     * @return 1 if deletion was successful otherwise 0
     */
    override fun delete(entity: CSVImportHistory): Int {
        return CsvImportHistories.deleteAll()
    }
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the CSVImportHistory entry
     * @return 1 if deletion was successful otherwise 0
     */
    override fun deleteById(id: Int): Int {
        return CsvImportHistories.deleteWhere { CsvImportHistories.id eq id }
    }
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    override fun deleteAll(): Int {
        return CsvImportHistories.deleteAll()
    }
    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE CSVImportHistories AUTO_INCREMENT = 1")
    }
}