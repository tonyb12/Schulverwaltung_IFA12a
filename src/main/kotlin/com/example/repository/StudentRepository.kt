package com.example.repository

import com.example.database.objects.Students
import com.example.repository.interfaces.IStudentRepository
import com.example.dto.Student
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentRepository : IStudentRepository {
    override fun getAll(): List<Student> {
        return Students.selectAll().map { Student.fromRow(it) }
    }

    override fun getById(id: Int): Student? {
            val student = Students.select { Students.id eq id }.singleOrNull() ?: return null
            return Student.fromRow(student)
    }

    override fun add(entity: Student): Student {
        val id = Students.insert {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
            it[className] = entity.className
            it[birthday] = entity.birthday
            it[email] = entity.email
        } get Students.id
        return Student.fromRow(Students.select{Students.id eq id}.single())
    }

    override fun add(entities: List<Student>): List<Student> {
        return Students.batchInsert(entities) {
            this[Students.firstName] = it.firstName
            this[Students.surName] = it.surName
            this[Students.className] = it.className
            this[Students.birthday] = it.birthday
            this[Students.email] = it.email
        }.toList().map { Student.fromRow(it) }
    }

    override fun update(entity: Student): Int {
        return Students.update {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
            it[className] = entity.className
            it[birthday] = entity.birthday
            it[email] = entity.email
        }
    }

    override fun delete(entity: Student): Int {
        return Students.deleteAll()
    }

    override fun deleteById(id: Int): Int {
        return Students.deleteWhere{ Students.id eq id }
    }

    override fun deleteAll(): Int {
        return Students.deleteAll()
    }

    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE Students AUTO_INCREMENT = 1")
    }
}

