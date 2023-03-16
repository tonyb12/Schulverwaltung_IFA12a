package com.schulverwaltung.mocks.repository

import com.schulverwaltung.dto.Student
import com.schulverwaltung.repository.interfaces.IStudentRepository
import org.jetbrains.exposed.sql.Transaction

class MockStudentRepository : IStudentRepository {
    private var mockTable = mutableListOf<Student>()
    override fun getAll(): List<Student> {
        return mockTable
    }

    override fun getById(id: Int): Student? {
        return mockTable.find {
            it.id == id
        }
    }

    override fun add(entity: Student): Student {
        var id = 1
        if (mockTable.isNotEmpty()) {
            mockTable.sortBy { it.id }
            id = mockTable.last().id
        }
        val tmpStudent = Student(id, entity.firstName, entity.surName, entity.className, entity.birthday, entity.email, entity.jobDesc)
        mockTable.add(tmpStudent)
        return tmpStudent
    }

    override fun add(entities: List<Student>): List<Student> {
        val newList = mutableListOf<Student>()
        for (entity in entities) {
            newList.add(this.add(entity))
        }
        return newList
    }

    override fun update(entity: Student): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Student): Int {
        val removed = mockTable.remove(entity)
        return if (removed) 1 else 0
    }

    override fun deleteById(id: Int): Int {
        val removed = mockTable.removeIf { it.id == id }
        return if (removed) 1 else 0
    }

    override fun deleteAll(): Int {
        val length = mockTable.count()
        mockTable.clear()
        return length
    }

    override fun resetAutoIncrement(transaction: Transaction) {}
}