package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.IStudentController
import com.schulverwaltung.dto.Student
import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.UnitOfWork
import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.PasswordHasher
import com.schulverwaltung.utils.UserNameGenerator

class StudentController : IStudentController {
    private val _unitOfWork: UnitOfWork = UnitOfWork()
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

    override suspend fun add(entity: Student): Student {
        val result = _unitOfWork.transactionMiddleware.newAsyncTransactionScope {
            val student = _unitOfWork.studentRepository.add(entity)
            val userName = UserNameGenerator.getUsername(entity.firstName, entity.surName, entity.birthday)
            _unitOfWork.studentSecretRepository.add(StudentSecret(0, userName, PasswordHasher.hashPassword(BirthdayParser.parse(entity.birthday)), student.id))
            _unitOfWork.commit()
            return@newAsyncTransactionScope student
        }
        return result.await()
    }

    override suspend fun add(entities: List<Student>): List<Student> {
        val result = _unitOfWork.transactionMiddleware.newAsyncTransactionScope {
            val students = _unitOfWork.studentRepository.add(entities)
            val secretList = mutableListOf<ISecret>()
            students.parallelStream().forEach {
                val userName = UserNameGenerator.getUsername(it.firstName,it.surName,it.birthday)
                secretList.add(StudentSecret(0, userName, PasswordHasher.hashPassword(BirthdayParser.parse(it.birthday)), it.id))
            }
            _unitOfWork.studentSecretRepository.add(secretList)
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