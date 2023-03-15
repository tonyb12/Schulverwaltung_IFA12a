package com.schulverwaltung

import com.schulverwaltung.controllers.SecretaryController
import com.schulverwaltung.controllers.SecretarySecretController
import com.schulverwaltung.controllers.StudentController
import com.schulverwaltung.controllers.StudentsSecretController
import com.schulverwaltung.controllers.interfaces.ISecretaryController
import com.schulverwaltung.controllers.interfaces.ISecretarySecretController
import com.schulverwaltung.controllers.interfaces.IStudentController
import com.schulverwaltung.controllers.interfaces.IStudentSecretController
import com.schulverwaltung.database.interfaces.ITransactionMiddleware
import com.schulverwaltung.dto.Student
import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.mocks.repository.MockSecretaryRepository
import com.schulverwaltung.mocks.repository.MockSecretarySecretRepository
import com.schulverwaltung.mocks.repository.MockStudentRepository
import com.schulverwaltung.mocks.repository.MockStudentsSecretRepository
import com.schulverwaltung.mocks.transactionmiddleware.MockTransactionMiddleWare
import com.schulverwaltung.mocks.unitofwork.MockUnitOfWork
import com.schulverwaltung.mocks.utils.MockPasswordHasher
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import com.schulverwaltung.repository.interfaces.ISecretarySecretRepository
import com.schulverwaltung.repository.interfaces.IStudentRepository
import com.schulverwaltung.repository.interfaces.IStudentSecretRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.UserNameGenerator
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import kotlin.test.*

class ControllerTests: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<IPasswordHasher> {MockPasswordHasher()}
                single<ITransactionMiddleware> {MockTransactionMiddleWare()}
                single<IBirthdayParser> {BirthdayParser()}
                singleOf(::UserNameGenerator) bind IUserNameGenerator::class
                singleOf(::MockStudentRepository) bind IStudentRepository::class
                singleOf(::MockStudentsSecretRepository) bind IStudentSecretRepository::class
                singleOf(::MockSecretaryRepository) bind ISecretaryRepository::class
                singleOf(::MockSecretarySecretRepository) bind ISecretarySecretRepository::class
                singleOf(::MockUnitOfWork) bind IUnitOfWork::class
                singleOf(::StudentController) bind IStudentController::class
                singleOf(::StudentsSecretController) bind IStudentSecretController::class
                singleOf(::SecretaryController) bind ISecretaryController::class
                singleOf(::SecretarySecretController) bind ISecretarySecretController::class
            }
        )
    }

    @Test
    fun `Should add a student`() = runBlocking {
        val studentController = get<IStudentController>()
        val studentSecretController = get<IStudentSecretController>()
        val passwordHasher = get<IPasswordHasher>()

        var students = studentController.getAll()
        assertTrue(students.isEmpty())

        studentController.add(Student(0, "Hans", "Peter", "IT10", "20.12.2022", "test.test@gmail.com"))
        students = studentController.getAll()
        assertFalse(students.isEmpty())

        val expectedStudentList = listOf(Student(1, "Hans", "Peter", "IT10", "20.12.2022", "test.test@gmail.com"))

        assertEquals(expectedStudentList, students)

        val expectedStudentSecretList = listOf(StudentSecret(1, "HanPet20221220", passwordHasher.hashPassword("20221220"), 1))
        val studentSecrets = studentSecretController.getAll()
        assertEquals(expectedStudentSecretList, studentSecrets)
    }
}