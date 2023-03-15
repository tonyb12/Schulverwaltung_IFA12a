package com.schulverwaltung.database

import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class ExposedTransactionMiddleware : ITransactionMiddleware {
    override suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T {
        return newSuspendedTransaction(Dispatchers.IO, db = ExposedDb.connection) {
            return@newSuspendedTransaction callback()
        }
    }

    override suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T> {
        return suspendedTransactionAsync(Dispatchers.IO, db = ExposedDb.connection) {
            return@suspendedTransactionAsync callback()
        }
    }
}