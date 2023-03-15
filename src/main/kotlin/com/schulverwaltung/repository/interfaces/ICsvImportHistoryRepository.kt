package com.schulverwaltung.repository.interfaces

import com.schulverwaltung.dto.CSVImportHistory

interface ICsvImportHistoryRepository : IRepository<CSVImportHistory, Int> {

    suspend fun getLatest(): CSVImportHistory
}