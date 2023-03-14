package com.schulverwaltung.database.exposed

import org.jetbrains.exposed.sql.Database

class ExposedDb {
    companion object {
        const val DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
        const val JDBC_URL = "jdbc:mysql://localhost:3306/db?rewriteBatchedStatements=true"
        val connection = Database.connect(JDBC_URL,DRIVER_CLASS_NAME,"root","root")
    }
}