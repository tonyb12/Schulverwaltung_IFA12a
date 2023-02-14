package com.example.database

import com.example.model.Student
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow

interface DAOFacade {

    suspend fun getAllStudents() : List<Student>
    suspend fun getStudent(id : Int) : Student?
    suspend fun editStudent(id : Int) : Boolean
    suspend fun editAllStudents(students : List<Student>) : Boolean
    suspend fun deleteStudent(id : Int) : Boolean
    suspend fun addStudents(id: Int, surname: String, lastname: String, class_name: String, birthday: String, email: String) : Student?
    suspend fun addStudent(surname: String, lastname: String, class_name: String, birthday: String, email: String): ResultRow?

}