package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.CsvImportHistories

import org.jetbrains.exposed.sql.ResultRow

/**
 * Csv Import History
 *
 * @property id
 * @property uploadTime
 * @property fileName
 */
data class CSVImportHistory(val id: Int, val uploadTime: String, val fileName: String) {
    companion object {

        /**
         * Converts a ResultRow from the database to a csv import history
         *
         * @param row that exposed returns
         * @return CSVImportHistory object
         */
        fun fromRow(row: ResultRow): CSVImportHistory = CSVImportHistory(
            id = row[CsvImportHistories.id],
            uploadTime = row[CsvImportHistories.uploadTime],
            fileName = row[CsvImportHistories.fileName]

        )
    }
}