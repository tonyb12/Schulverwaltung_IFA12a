package com.schulverwaltung.controller

import com.schulverwaltung.exceptions.MultiException
import com.schulverwaltung.controller.interfaces.IStudentController
import com.schulverwaltung.dto.Student
import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import java.time.format.DateTimeParseException

/**
 * StudentController
 *
 * @property _unitOfWork
 * @property _passwordHasher
 * @property _userNameGenerator
 * @property _birthdayParser
 */
open class StudentController(
    private val _unitOfWork: IUnitOfWork,
    private val _passwordHasher: IPasswordHasher,
    private val _userNameGenerator: IUserNameGenerator,
    private val _birthdayParser: IBirthdayParser
) : IStudentController {
    override suspend fun getAll(): List<Student> {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): Student? {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentRepository.getById(id)
        }
    }

    /**
     * Adds a student to the repository and creates a student secret. If an error occurs a rollback happens otherwise a commit to the database
     *
     * @param entity as a student
     * @return a student that has been added to the database
     * @throws DateTimeParseException
     */
    @Throws(DateTimeParseException::class)
    override suspend fun add(entity: Student): Student {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            try {
                val student = _unitOfWork.studentRepository.add(entity)

                val userName = _userNameGenerator.getUsername(entity.firstName, entity.surName, entity.birthday)
                _unitOfWork.studentSecretRepository.add(
                    StudentSecret(
                        0,
                        userName,
                        _passwordHasher.hashPassword(_birthdayParser.parse(entity.birthday)),
                        student.id
                    )
                )
                _unitOfWork.commit()
                return@newTransactionScope student
            } catch (e: Exception) {
                _unitOfWork.rollback()
                throw e
            }
        }
    }

    /**
     * Adds a list of students to the repository and creates their secrets. If an exception occurs, it will be cached.
     * At the end all students where an error occurred are removed from the repository -> Partial rollback
     *
     * @param entities
     * @return a list of students
     * @throws MultiException
     */
    @Throws(MultiException::class)
    override suspend fun add(entities: List<Student>): List<Student> {
        val result = _unitOfWork.transactionMiddleware.newAsyncTransactionScope {
            if (entities.isEmpty()) {
                return@newAsyncTransactionScope listOf<Student>()
            }
            val errorList = mutableListOf<Exception>()
            val failedEntities = mutableListOf<Int>()

            val students = _unitOfWork.studentRepository.add(entities)
            val secretList = mutableListOf<ISecret>()
            students.parallelStream().forEach {
                try {
                    val userName = _userNameGenerator.getUsername(it.firstName, it.surName, it.birthday)
                    secretList.add(
                        StudentSecret(
                            0,
                            userName,
                            _passwordHasher.hashPassword(_birthdayParser.parse(it.birthday)),
                            it.id
                        )
                    )
                } catch (e: Exception) {
                    failedEntities.add(it.id)
                    errorList.add(e)
                }
            }
            _unitOfWork.studentSecretRepository.add(secretList)

            if (failedEntities.isNotEmpty()) {
                failedEntities.forEach {
                    _unitOfWork.studentRepository.deleteById(it)
                }
                throw MultiException(errorList)
            }

            _unitOfWork.commit()
            return@newAsyncTransactionScope students
        }
        return result.await()
    }

    override suspend fun update(entity: Student): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentRepository.update(entity)
        }
    }

    override suspend fun delete(entity: Student): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            _unitOfWork.studentRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return _unitOfWork.transactionMiddleware.newTransactionScope {
            val result = _unitOfWork.studentRepository.deleteAll()
            _unitOfWork.studentRepository.resetAutoIncrement(this)
            return@newTransactionScope result
        }
    }
}