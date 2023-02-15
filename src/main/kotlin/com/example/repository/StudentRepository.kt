package com.example.repository

import com.example.database.objects.Students
import com.example.repository.interfaces.IStudentRepository
import com.example.model.Student
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class StudentRepository : IStudentRepository {
    private val connection: Database
    constructor(connection: Database) {
        this.connection = connection
    }
    override fun getAll(): List<Student> {
        return transaction(connection) {
            Students.selectAll().map { Student.fromRow(it) }
        }
    }

    override fun getById(id: Int): Student? {
        return transaction(connection) {
            val student = Students.select { Students.id eq id }.singleOrNull() ?: return@transaction null
            Student.fromRow(student)
        }
    }

    override fun add(entity: Student): Student {
        return transaction(connection) {
            val id = Students.insert {
                it[Students.firstName] = entity.firstName
                it[Students.surName] = entity.surName
                it[Students.className] = entity.className
                it[Students.birthday] = entity.birthday
                it[Students.email] = entity.email
            } get Students.id
            Student.fromRow(Students.select{Students.id eq id}.single())
        }
    }

    override fun update(entity: Student): Int {
        return transaction(connection) {
            Students.update {
                it[Students.firstName] = entity.firstName
                it[Students.surName] = entity.surName
                it[Students.className] = entity.className
                it[Students.birthday] = entity.birthday
                it[Students.email] = entity.email
            }
        }
    }

    override fun delete(entity: Student): Int {
        return transaction(connection) {
            Students.deleteAll()
        }
    }

    override fun deleteById(id: Int): Int {
        return transaction(connection) {
            Students.deleteWhere{ Students.id eq id }
        }
    }

    override fun deleteAll(): Int {
        return transaction(connection) {
            Students.deleteAll()
        }
    }
}

