package com.schulverwaltung.controller

import com.schulverwaltung.controller.interfaces.ISecretaryController
import com.schulverwaltung.controller.interfaces.ISecretarySecretController
import com.schulverwaltung.controller.interfaces.IStudentController
import com.schulverwaltung.controller.interfaces.IStudentSecretController
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.dto.Secretary
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
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import kotlin.test.*

class SecretaryControllerTest : KoinTest {

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
    fun `Should add a secretary and its secret correctly`() = runBlocking {
        val secretaryController = get<ISecretaryController>()
        val secretarySecretController = get<ISecretarySecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var secretaries = secretaryController.getAll()
        assertTrue(secretaries.isEmpty())


        secretaryController.add(
            Secretary(
                0,
                "Hans",
                "Peter"
            )
        )

        secretaries = secretaryController.getAll()
        assertFalse(secretaries.isEmpty())

        val expectedStudentList = listOf(
            Secretary(
                1,
                "Hans",
                "Peter"
            )
        )

        assertEquals(expectedStudentList, secretaries)

        val expectedSecretarySecretList =
            listOf(SecretarySecret(1, "Hans", passwordHasher.hashPassword("Hans"), 1))
        val secretarySecrets = secretarySecretController.getAll()
        assertEquals(expectedSecretarySecretList, secretarySecrets)
    }

    @Test
    fun `Should add multiple secretaries and its secrets correctly`(): Unit = runBlocking {
        val secretaryController = get<ISecretaryController>()
        val secretarySecretController = get<ISecretarySecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var secretaries = secretaryController.getAll()
        assertTrue(secretaries.isEmpty())


        secretaryController.add(
            listOf(
                Secretary(
                    0,
                    "Hans",
                    "Peter"
                ),
                Secretary(
                    0,
                    "Toksan",
                    "Hakan"
                )
            )
        )

        secretaries = secretaryController.getAll()
        assertFalse(secretaries.isEmpty())

        val expectedSecretaryList = listOf(
            Secretary(
                1,
                "Hans",
                "Peter"
            ),
            Secretary(
                2,
                "Toksan",
                "Hakan"
            )
        )

        assertEquals(expectedSecretaryList, secretaries)

        val secretarySecrets = secretarySecretController.getAll()

        var secret =
            secretarySecrets.find { it.userName == "Toksan" && it.hash == passwordHasher.hashPassword("Toksan") && it.userId == 2 }
        assertNotNull(secret)
        secret =
            secretarySecrets.find { it.userName == "Hans" && it.hash == passwordHasher.hashPassword("Hans") && it.userId == 1 }
        assertNotNull(secret)
    }

    @Test
    fun `Should delete all entries and reset the autoincrement`() = runBlocking {
        val secretaryController = get<ISecretaryController>()

        var students = secretaryController.getAll()
        assertTrue(students.isEmpty())


        secretaryController.add(
            listOf(
                Secretary(
                    0,
                    "Hans",
                    "Peter"
                ),
                Secretary(
                    0,
                    "Toksan",
                    "Hakan"
                )
            )
        )

        val result = secretaryController.deleteAll()
        assertEquals(2, result)
        assertEquals(1, (secretaryController as MockSecretaryControllerExtension).getAutoIncrementResetCount())
    }
}