package com.schulverwaltung.repository.interfaces

import com.schulverwaltung.dto.CSVImportHistory

interface ICsvImportHistoryRepository : IRepository<CSVImportHistory, Int> {

    fun getLatest(): CSVImportHistory
}