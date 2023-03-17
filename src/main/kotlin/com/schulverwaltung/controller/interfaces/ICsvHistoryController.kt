package com.schulverwaltung.controller.interfaces

import com.schulverwaltung.dto.CSVImportHistory

interface ICsvHistoryController : IController<CSVImportHistory, Int> {
    /**
     * Gets the latest CSVImportHistory
     *
     * @return CSVImportHistory or null
     */
    suspend fun getLatest(): CSVImportHistory?

}