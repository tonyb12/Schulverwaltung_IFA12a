package com.schulverwaltung.mocks.transactionmiddleware

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.jetbrains.exposed.sql.Transaction
import org.mockito.Mockito

class MockTransactionMiddleWare: ITransactionMiddleware {
    override suspend fun <T> newTransactionScope(callback: Transaction.() -> T): T {
        val transaction = Mockito.mock(Transaction::class.java)
        return callback(transaction)
    }

    override suspend fun <T> newAsyncTransactionScope(callback: Transaction.() -> T): Deferred<T> {
        val transaction = Mockito.mock(Transaction::class.java)
        return CompletableDeferred(callback(transaction))
    }
}