package com.example.controllers

import com.example.controllers.interfaces.IStudentController
import com.example.database.exposed.ExposedDb
import com.example.dto.Student
import com.example.unitofwork.UnitOfWork
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
            _unitOfWork.commit()
            return@suspendedTransactionAsync student
        }
        return result.await()
    }

    override suspend fun add(entities: List<Student>): List<Student> {
        val result = suspendedTransactionAsync(Dispatchers.IO, db = _unitOfWork.databaseConnection) {
            val students = _unitOfWork.studentRepository.add(entities)
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
             _unitOfWork.studentRepository.deleteAll()
        }
    }
}