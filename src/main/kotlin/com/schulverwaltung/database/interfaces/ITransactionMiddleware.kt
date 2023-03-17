package com.schulverwaltung.database.interfaces

import kotlinx.coroutines.Deferred
import org.jetbrains.exposed.sql.Transaction

/**
 * Interface that defines a middleware to centrally handle transactions
 *
 */
interface ITransactionMiddleware {
    /**
     * New transaction scope to wrap a statement / callback inside a transaction
     *
     * @param T return type of the transaction
     * @param callback code that will be executed
     * @return T
     */
    suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T

    /**
     * New async transaction scope to wrap a statement / callback inside a transaction and return its deferred value
     *
     * @param T return type of the transaction
     * @param callback code that will be executed
     * @return Deferred T
     */
    suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T>
}