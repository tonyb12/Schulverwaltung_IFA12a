package com.schulverwaltung.repository.interfaces

import com.schulverwaltung.dto.CSVImportHistory

interface ICsvImportHistoryRepository : IRepository<CSVImportHistory, Int> {
    /**
     * Gets the latest entry sorted by the id
     *
     * @return CSVImportHistory or null
     */
    fun getLatest(): CSVImportHistory?
}