package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.CsvImportHistories

import org.jetbrains.exposed.sql.ResultRow

data class CSVImportHistory(val id: Int, val uploadTime: String, val fileName: String) {
    companion object{

        fun fromRow(row: ResultRow): CSVImportHistory = CSVImportHistory(
            id = row[CsvImportHistories.id],
            uploadTime = row[CsvImportHistories.uploadTime],
            fileName = row[CsvImportHistories.fileName]

        )
    }
}