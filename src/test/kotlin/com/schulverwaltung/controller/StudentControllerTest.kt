package com.schulverwaltung.controller

import com.schulverwaltung.exceptions.MultiException
import com.schulverwaltung.controller.interfaces.ISecretaryController
import com.schulverwaltung.controller.interfaces.ISecretarySecretController
import com.schulverwaltung.controller.interfaces.IStudentController
import com.schulverwaltung.controller.interfaces.IStudentSecretController
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.dto.Student
import com.schulverwaltung.dto.StudentSecret
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
import java.time.format.DateTimeParseException
import kotlin.test.*

class StudentControllerTest : KoinTest {

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
    fun `Should add a student and its secret correctly`() = runBlocking {
        val studentController = get<IStudentController>()
        val studentSecretController = get<IStudentSecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())


        studentController.add(
            Student(
                0,
                "Hans",
                "Peter",
                "IT10",
                "20.12.2022",
                "test.test@gmail.com",
                "Fachinformatiker - Anwendungsentwicklung"
            )
        )

        students = studentController.getAll()
        assertFalse(students.isEmpty())

        val expectedStudentList = listOf(
            Student(
                1,
                "Hans",
                "Peter",
                "IT10",
                "20.12.2022",
                "test.test@gmail.com",
                "Fachinformatiker - Anwendungsentwicklung"
            )
        )

        assertEquals(expectedStudentList, students)

        val expectedStudentSecretList =
            listOf(StudentSecret(1, "HanPet20221220", passwordHasher.hashPassword("20221220"), 1))
        val studentSecrets = studentSecretController.getAll()
        assertEquals(expectedStudentSecretList, studentSecrets)
    }

    @Test
    fun `Should add multiple students and its secrets correctly`(): Unit = runBlocking {
        val studentController = get<IStudentController>()
        val studentSecretController = get<IStudentSecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())


        studentController.add(
            listOf(
                Student(
                    0,
                    "Hans",
                    "Peter",
                    "IT10",
                    "20.12.2022",
                    "test.test@gmail.com",
                    "Fachinformatiker - Anwendungsentwicklung"
                ),
                Student(
                    0,
                    "Toksan",
                    "Hakan",
                    "IT10",
                    "12.03.2022",
                    "Toksan.Hakan@gmail.com",
                    "Fachinformatiker - Anwendungsentwicklung"
                )
            )
        )

        students = studentController.getAll()
        assertFalse(students.isEmpty())

        val expectedStudentList = listOf(
            Student(
                1,
                "Hans",
                "Peter",
                "IT10",
                "20.12.2022",
                "test.test@gmail.com",
                "Fachinformatiker - Anwendungsentwicklung"
            ),
            Student(
                2,
                "Toksan",
                "Hakan",
                "IT10",
                "12.03.2022",
                "Toksan.Hakan@gmail.com",
                "Fachinformatiker - Anwendungsentwicklung"
            )
        )

        assertEquals(expectedStudentList, students)

        val studentSecrets = studentSecretController.getAll()

        var secret =
            studentSecrets.find { it.userName == "TokHak20220312" && it.hash == passwordHasher.hashPassword("20220312") && it.userId == 2 }
        assertNotNull(secret)
        secret =
            studentSecrets.find { it.userName == "HanPet20221220" && it.hash == passwordHasher.hashPassword("20221220") && it.userId == 1 }
        assertNotNull(secret)
    }

    @Test
    fun `Should fail to add a student and its secret`(): Unit = runBlocking {
        val studentController = get<IStudentController>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())

        val birthday = "20.12.23"
        assertFailsWith(
            DateTimeParseException::class,
            block = {
                studentController.add(
                    Student(
                        0,
                        "Hans",
                        "Peter",
                        "IT10",
                        birthday,
                        "test.test@gmail.com",
                        "Fachinformatiker - Anwendungsentwicklung"
                    )
                )
            })

        students = studentController.getAll()
        assertTrue(students.isEmpty())
    }

    @Test
    fun `Should fail to add a student and its secret when the first name is empty`(): Unit = runBlocking {
        val studentController = get<IStudentController>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())

        val birthday = "20.12.2023"
        assertFailsWith(
            IllegalArgumentException::class,
            block = {
                studentController.add(
                    Student(
                        0,
                        "",
                        "Peter",
                        "IT10",
                        birthday,
                        "test.test@gmail.com",
                        "Fachinformatiker - Anwendungsentwicklung"
                    )
                )
            })

        students = studentController.getAll()
        assertTrue(students.isEmpty())
    }

    @Test
    fun `Should fail to add a student and its secret when the last name is empty`(): Unit = runBlocking {
        val studentController = get<IStudentController>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())

        val birthday = "20.12.2023"
        assertFailsWith(
            IllegalArgumentException::class,
            block = {
                studentController.add(
                    Student(
                        0,
                        "Hans",
                        "",
                        "IT10",
                        birthday,
                        "test.test@gmail.com",
                        "Fachinformatiker - Anwendungsentwicklung"
                    )
                )
            })

        students = studentController.getAll()
        assertTrue(students.isEmpty())
    }

    @Test
    fun `Should add one student and its secret correctly and fail for the other one`(): Unit = runBlocking {
        val studentController = get<IStudentController>()
        val studentSecretController = get<IStudentSecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())

        val exception = assertFailsWith(MultiException::class, block = {
            studentController.add(
                listOf(
                    Student(
                        0,
                        "Hans",
                        "Peter",
                        "IT10",
                        "20.12.2022",
                        "test.test@gmail.com",
                        "Fachinformatiker - Anwendungsentwicklung"
                    ),
                    Student(
                        0,
                        "Toksan",
                        "Hakan",
                        "IT10",
                        "12.03.20",
                        "Toksan.Hakan@gmail.com",
                        "Fachinformatiker - Anwendungsentwicklung"
                    )
                )
            )
        })

        assertEquals(1, exception.exceptionList.count())



        students = studentController.getAll()
        assertFalse(students.isEmpty())

        val expectedStudentList = listOf(
            Student(
                1,
                "Hans",
                "Peter",
                "IT10",
                "20.12.2022",
                "test.test@gmail.com",
                "Fachinformatiker - Anwendungsentwicklung"
            )
        )

        assertEquals(expectedStudentList, students)

        val studentSecrets = studentSecretController.getAll()
        val secret =
            studentSecrets.find { it.userName == "HanPet20221220" && it.hash == passwordHasher.hashPassword("20221220") && it.userId == 1 }
        assertNotNull(secret)
        assertEquals(1, studentSecrets.count())
    }

    @Test
    fun `Should delete all entries and reset the autoincrement`() = runBlocking {
        val studentController = get<IStudentController>()

        val students = studentController.getAll()
        assertTrue(students.isEmpty())


        studentController.add(
            listOf(
                Student(
                    0,
                    "Hans",
                    "Peter",
                    "IT10",
                    "20.12.2022",
                    "test.test@gmail.com",
                    "Fachinformatiker - Anwendungsentwicklung"
                ),
                Student(
                    0,
                    "Toksan",
                    "Hakan",
                    "IT10",
                    "12.03.2022",
                    "Toksan.Hakan@gmail.com",
                    "Fachinformatiker - Anwendungsentwicklung"
                )
            )
        )

        val result = studentController.deleteAll()
        assertEquals(2, result)
        assertEquals(1, (studentController as MockStudentControllerExtension).getAutoIncrementResetCount())
    }
}

