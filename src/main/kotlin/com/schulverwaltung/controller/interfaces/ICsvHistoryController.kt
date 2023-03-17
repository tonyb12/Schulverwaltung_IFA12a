package com.schulverwaltung.controller.interfaces

import com.schulverwaltung.dto.CSVImportHistory

interface ICsvHistoryController : IController<CSVImportHistory, Int> {

    suspend fun getLatest(): CSVImportHistory?

}