package com.schulverwaltung.database

import com.schulverwaltung.database.exposed.ExposedDb
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

/**
 * Class that defines a middleware to centrally handle transactions
 *
 */
class ExposedTransactionMiddleware : ITransactionMiddleware {
    /**
     * New transaction scope to wrap a statement / callback inside a transaction
     *
     * @param T return type of the transaction
     * @param callback code that will be executed
     * @return T
     */
    override suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T {
        return newSuspendedTransaction(Dispatchers.IO, db = ExposedDb.connection) {
            return@newSuspendedTransaction callback()
        }
    }

    /**
     * New async transaction scope to wrap a statement / callback inside a transaction and return its deferred value
     *
     * @param T return type of the transaction
     * @param callback code that will be executed
     * @return Deferred T
     */
    override suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T> {
        return suspendedTransactionAsync(Dispatchers.IO, db = ExposedDb.connection) {
            return@suspendedTransactionAsync callback()
        }
    }
}