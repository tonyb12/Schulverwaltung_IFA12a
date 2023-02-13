package com.example.database

import com.example.database.DatabaseFactory.dbQuery
import com.example.model.Student
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import com.example.model.Student.Students
import kotlinx.coroutines.runBlocking

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToStudent(row: ResultRow) = Student(
        id = row[Students.id],
        surname = row[Students.surname],
        lastname = row[Students.lastname],
        class_name = row[Students.class_name],
        birthday = row[Students.birthday],
        email = row[Students.email]

    )
    override suspend fun getAllStudents(): List<Student> = dbQuery {

            Students.selectAll().map(:: resultRowToStudent)

    }

    override suspend fun getStudent(id: Int): Student? = dbQuery{

        Students
            .select{Students.id eq id}
            .map(:: resultRowToStudent)
            .singleOrNull()
    }

    override suspend fun editStudent(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun editAllStudents(students: List<Student>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStudent(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addStudents(
        id: Int,
        surname: String,
        lastname: String,
        class_name: String,
        birthday: String,
        email: String
    ): Student? = dbQuery{

        val insertStatement = Students.insert {

                it[Students.id] = id;
                it[Students.surname] = surname;
                it[Students.lastname] = lastname;
                it[Students.class_name] = class_name;
                it[Students.birthday] = birthday;
                it[Students.email] = birthday;
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToStudent)
    }


}

val dao : DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(getAllStudents().isEmpty()){
            addStudents(1,"Bui","Toan","IFA12a","12.12.1998", "tony.bui267@gmail.com")
        }
    }
}