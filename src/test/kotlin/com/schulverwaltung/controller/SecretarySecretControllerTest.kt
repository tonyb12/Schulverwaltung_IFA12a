package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.ISecretaryController
import com.schulverwaltung.controller.interfaces.ISecretarySecretController
import com.schulverwaltung.controller.interfaces.IStudentController
import com.schulverwaltung.controller.interfaces.IStudentSecretController
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.mocks.controller.MockSecretaryControllerExtension
import com.schulverwaltung.mocks.controller.MockSecretarySecretControllerExtension
import com.schulverwaltung.mocks.controller.MockStudentControllerExtension
import com.schulverwaltung.mocks.controller.MockStudentSecretControllerExtension
import com.schulverwaltung.mocks.repository.*
import com.schulverwaltung.mocks.transactionmiddleware.MockTransactionMiddleWare
import com.schulverwaltung.mocks.unitofwork.MockUnitOfWork
import com.schulverwaltung.mocks.utils.MockPasswordHasher
import com.schulverwaltung.repository.interfaces.*
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.UserNameGenerator
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SecretarySecretControllerTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            factory<IPasswordHasher> { MockPasswordHasher() }
            factory<ITransactionMiddleware> { MockTransactionMiddleWare() }
            factory<IBirthdayParser> { BirthdayParser() }
            factory<IUserNameGenerator> { UserNameGenerator(get()) }
            single<IStudentRepository> { MockStudentRepository() }
            single<IStudentSecretRepository> { MockStudentSecretRepository() }
            single<ISecretaryRepository> { MockSecretaryRepository() }
            single<ISecretarySecretRepository> { MockSecretarySecretRepository() }
            single<ICsvImportHistoryRepository> { MockCsvImportHistoryRepository() }
            single<IUnitOfWork> { MockUnitOfWork(get(), get(), get(), get(), get(), get()) }
            factory<IStudentController> { MockStudentControllerExtension(get(), get(), get(), get()) }
            factory<IStudentSecretController> { MockStudentSecretControllerExtension(get()) }
            factory<ISecretaryController> { MockSecretaryControllerExtension(get(), get()) }
            factory<ISecretarySecretController> { MockSecretarySecretControllerExtension(get()) }
        })
    }

    @Test
    fun `Should delete all entries and reset the autoincrement`() = runBlocking {
        val secretarySecretController = get<ISecretarySecretController>()

        var secretarySecrets = secretarySecretController.getAll()
        assertTrue(secretarySecrets.isEmpty())


        secretarySecretController.add(
            listOf(
                SecretarySecret(
                    0,
                    "Hans",
                    "fasjklsdfjkajk;b",
                    1
                ),
                SecretarySecret(
                    0,
                    "Toksan",
                    "fasjklsdfjkajk;b",
                    1
                )
            )
        )

        val result = secretarySecretController.deleteAll()
        assertEquals(2, result)
        assertEquals(
            1,
            (secretarySecretController as MockSecretarySecretControllerExtension).getAutoIncrementResetCount()
        )
    }
}