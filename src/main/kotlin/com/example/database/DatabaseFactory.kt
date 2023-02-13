package com.example.database

import com.example.model.Student
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){

        val driverClassName = "com.mysql.cj.jdbc.Driver";
        val jdbcURL = "jdbc:mysql://localhost:3306/db";

        val database = Database.connect(jdbcURL,driverClassName,"root","root");

        transaction(database) {

            SchemaUtils.create(Student.Students);
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}