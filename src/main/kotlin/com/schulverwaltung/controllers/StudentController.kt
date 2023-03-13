package com.schulverwaltung.controllers

import com.schulverwaltung.controllers.interfaces.IStudentController
import com.schulverwaltung.dto.Student
import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.unitofwork.UnitOfWork
import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.PasswordHasher
import com.schulverwaltung.utils.UserNameGenerator
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class StudentController : IStudentController {
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override suspend fun getAll(): List<Student> {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentRepository.getAll()
        }
    }

    override suspend fun getById(id: Int): Student? {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            _unitOfWork.studentRepository.getById(id)
        }
    }

    override suspend fun add(entity: Student): Student {
        val result = suspendedTransactionAsync(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val student = _unitOfWork.studentRepository.add(entity)
            val userName = UserNameGenerator.getUsername(entity.firstName, entity.surName, entity.birthday)
            _unitOfWork.studentSecretRepository.add(StudentSecret(0, userName, PasswordHasher.hashPassword(BirthdayParser.parse(entity.birthday)), student.id))
            _unitOfWork.commit()
            return@suspendedTransactionAsync student
        }
        return result.await()
    }

    override suspend fun add(entities: List<Student>): List<Student> {
        val result = suspendedTransactionAsync(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val students = _unitOfWork.studentRepository.add(entities)
            val secretList = mutableListOf<ISecret>()
            students.parallelStream().forEach {
                val userName = UserNameGenerator.getUsername(it.firstName,it.surName,it.birthday)
                secretList.add(StudentSecret(0, userName, PasswordHasher.hashPassword(BirthdayParser.parse(it.birthday)), it.id))
            }
            _unitOfWork.studentSecretRepository.add(secretList)
            _unitOfWork.commit()
            return@suspendedTransactionAsync students
        }
        return result.await()
    }

    override suspend fun update(entity: Student): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.studentRepository.update(entity)
        }
    }

    override suspend fun delete(entity: Student): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.studentRepository.delete(entity)
        }
    }

    override suspend fun deleteById(id: Int): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             _unitOfWork.studentRepository.deleteById(id)
        }
    }

    override suspend fun deleteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
             val result = _unitOfWork.studentRepository.deleteAll()
            _unitOfWork.studentRepository.resetAutoIncrement(this)
            return@newSuspendedTransaction result
        }
    }
}