package com.schulverwaltung.database

import com.schulverwaltung.database.exposed.ExposedDb
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync


interface ITransactionMiddleware<K> {
    suspend fun <T> newTransactionScope(callback: K.() -> T): T
    suspend fun <T> newAsyncTransactionScope(callback: K.() -> T): Deferred<T>
}

class ExposedTransactionMiddleware : ITransactionMiddleware<Transaction> {
    override suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T {
        return newSuspendedTransaction(Dispatchers.IO, db =  ExposedDb.connection) {
            return@newSuspendedTransaction callback()
        }
    }
    override suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T> {
        return suspendedTransactionAsync(Dispatchers.IO, db = ExposedDb.connection) {
            return@suspendedTransactionAsync callback()
        }
    }
}