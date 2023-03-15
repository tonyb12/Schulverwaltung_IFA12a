package com.schulverwaltung.controllers.interfaces

import com.schulverwaltung.dto.CSVImportHistory
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface ICsvHistoryController : IController<CSVImportHistory,Int> {

    suspend fun getLatest(): CSVImportHistory

}