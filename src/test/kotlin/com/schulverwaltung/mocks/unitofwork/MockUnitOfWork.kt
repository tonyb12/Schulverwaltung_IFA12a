package com.schulverwaltung.mocks.unitofwork

import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.mocks.actions.Action
import com.schulverwaltung.mocks.repository.MockStudentRepository
import com.schulverwaltung.repository.interfaces.*
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import org.jetbrains.exposed.sql.Database
import org.mockito.Mockito

class MockUnitOfWork(
    private val _transactionMiddleware: ITransactionMiddleware,
    private var _secretaryRepository: ISecretaryRepository,
    private var _studentRepository: IStudentRepository,
    private var _secretarySecretRepository: ISecretarySecretRepository,
    private var _studentSecretRepository: IStudentSecretRepository,
    private var _csvImportHistoryRepository: ICsvImportHistoryRepository
) : IUnitOfWork {
    override val databaseConnection: Database
        get() = Mockito.mock(Database::class.java)
    override val transactionMiddleware: ITransactionMiddleware
        get() = _transactionMiddleware

    override val secretaryRepository: ISecretaryRepository
        get() = _secretaryRepository

    override val studentRepository: IStudentRepository
        get() = _studentRepository
    override val secretarySecretRepository: ISecretarySecretRepository
        get() = _secretarySecretRepository
    override val studentSecretRepository: IStudentSecretRepository
        get() = _studentSecretRepository
    override val csvImportHistoryRepository: ICsvImportHistoryRepository
        get() = _csvImportHistoryRepository

    override fun commit() {
    }

    override fun rollback() {
        val caller = Thread.currentThread().stackTrace[2]
        if (caller.className.contains("StudentController\$add")) {
            val action = (_studentRepository as MockStudentRepository).actionStack.peek()
            when (action.first) {
                (Action.Add) -> _studentRepository.deleteById(action.second.id)
                else -> {}
            }
        }
    }
}