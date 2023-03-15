package com.schulverwaltung.database.interfaces

import kotlinx.coroutines.Deferred
import org.jetbrains.exposed.sql.Transaction

interface ITransactionMiddleware {
    suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T
    suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T>
}