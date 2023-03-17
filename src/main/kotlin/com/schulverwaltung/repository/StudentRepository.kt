package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.Students
import com.schulverwaltung.dto.Student
import com.schulverwaltung.repository.interfaces.IStudentRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentRepository : IStudentRepository {
    /**
     * Selects all entries from the database
     *
     * @return List of Students
     */
    override fun getAll(): List<Student> {
        return Students.selectAll().map { Student.fromRow(it) }
    }

    /**
     * Gets an entry by its id
     *
     * @param id of Student
     * @return Student or null
     */
    override fun getById(id: Int): Student? {
        val student = Students.select { Students.id eq id }.singleOrNull() ?: return null
        return Student.fromRow(student)
    }

    /**
     * Inserts an entity into the table
     *
     * @param entity Student
     * @return Student
     */
    override fun add(entity: Student): Student {
        val id = Students.insert {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
            it[className] = entity.className
            it[birthday] = entity.birthday
            it[email] = entity.email
            it[jobDesc] = entity.jobDesc
        } get Students.id
        return Student.fromRow(Students.select { Students.id eq id }.single())
    }
    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of Student
     * @return List of Student
     */
    override fun add(entities: List<Student>): List<Student> {
        return Students.batchInsert(entities) {
            this[Students.firstName] = it.firstName
            this[Students.surName] = it.surName
            this[Students.className] = it.className
            this[Students.birthday] = it.birthday
            this[Students.email] = it.email
            this[Students.jobDesc] = it.jobDesc
        }.toList().map { Student.fromRow(it) }
    }

    /**
     * Updates an entry in the table
     *
     * @param entity Student
     * @return 1 if change was successful otherwise 0
     */
    override fun update(entity: Student): Int {
        return Students.update({Students.id eq entity.id}) {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
            it[className] = entity.className
            it[birthday] = entity.birthday
            it[email] = entity.email
            it[jobDesc] = entity.jobDesc
        }
    }

    /**
     * Removes an entry from the table
     *
     * @param entity Student
     * @return 1 if deletion was successful otherwise 0
     */
    override fun delete(entity: Student): Int {
        return Students.deleteAll()
    }
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the Student entry
     * @return 1 if deletion was successful otherwise 0
     */
    override fun deleteById(id: Int): Int {
        return Students.deleteWhere { Students.id eq id }
    }
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    override fun deleteAll(): Int {
        return Students.deleteAll()
    }

    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE Students AUTO_INCREMENT = 1")
    }
}

