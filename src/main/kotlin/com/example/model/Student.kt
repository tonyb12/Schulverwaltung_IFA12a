package com.example.model

import org.jetbrains.exposed.sql.Table

data class Student(val id : Int, val surname: String, val lastname: String, val class_name : String, val birthday: String, val email: String ){

    object Students : Table(){

        val id = integer("id").autoIncrement()
        val surname = varchar("surname",1024)
        val lastname = varchar("lastname", 1024)
        val class_name = varchar("classname", 1024)
        val birthday = varchar("birthday", 1024)
        val email = varchar("email", 1024)

        override val primaryKey = PrimaryKey(id)
    }

    fun getUsername() : String{

        return createUsername();
    }

    private fun createUsername() : String{

        return surname.substring(0,3) + lastname.substring(0,3) + birthday;
    }

}