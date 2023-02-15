package com.example.controllers

import com.example.controllers.interfaces.IStudentController
import com.example.database.exposed.ExposedDb
import com.example.model.Student
import com.example.unitofwork.UnitOfWork

class StudentController : IStudentController {
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override fun getAll(): List<Student> {
        return _unitOfWork.studentRepository.getAll()
    }

    override fun getById(id: Int): Student? {
        return _unitOfWork.studentRepository.getById(id)
    }

    override fun add(entity: Student): Student {
        return _unitOfWork.studentRepository.add(entity)
    }

    override fun add(entity: List<Student>): List<Student> {
        TODO("Not yet implemented")
    }

    override fun update(entity: Student): Int {
        return _unitOfWork.studentRepository.update(entity)
    }

    override fun delete(entity: Student): Int {
        return _unitOfWork.studentRepository.delete(entity)
    }

    override fun deleteById(id: Int): Int {
        return _unitOfWork.studentRepository.deleteById(id)
    }

    override fun deleteAll(): Int {
        return _unitOfWork.studentRepository.deleteAll()
    }
}