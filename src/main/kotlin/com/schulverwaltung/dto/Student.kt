package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.Students
import org.jetbrains.exposed.sql.ResultRow

/**
 * Student
 *
 * @property id
 * @property firstName
 * @property surName
 * @property className
 * @property birthday
 * @property email
 * @property jobDesc
 */
data class Student(
    val id: Int,
    val firstName: String,
    val surName: String,
    val className: String,
    val birthday: String,
    val email: String,
    val jobDesc: String
) {
    companion object {
        /**
         * Converts a ResultRow from the database to a student
         *
         * @param row that exposed returns
         * @return Student object
         */
        fun fromRow(row: ResultRow): Student = Student(
            id = row[Students.id].value,
            firstName = row[Students.firstName],
            surName = row[Students.surName],
            className = row[Students.className],
            birthday = row[Students.birthday],
            email = row[Students.email],
            jobDesc = row[Students.jobDesc]
        )
    }
}