package com.example.dto

import com.example.database.objects.Students
import org.jetbrains.exposed.sql.ResultRow

data class Student(val id : Int, val firstName: String, val surName: String, val className : String, val birthday: String, val email: String ){
    companion object {
        fun fromRow(row: ResultRow): Student = Student(
                id = row[Students.id],
                firstName = row[Students.firstName],
                surName = row[Students.surName],
                className = row[Students.className],
                birthday = row[Students.birthday],
                email = row[Students.email]
        )
    }
}