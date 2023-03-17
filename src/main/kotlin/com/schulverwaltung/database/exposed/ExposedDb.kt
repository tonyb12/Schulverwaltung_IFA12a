package com.schulverwaltung.database.exposed

import org.jetbrains.exposed.sql.Database

/**
 * ExposedDb carries the database connection
 * Acts as a singleton
 *
 * @constructor Create empty Exposed db
 */
class ExposedDb {
    companion object {
        /**
         * Driver Class Name
         */
        private const val DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"

        /**
         * Jdbc Url and settings for the database connection
         */
        private const val JDBC_URL = "jdbc:mysql://localhost:33306/db?rewriteBatchedStatements=true"

        /**
         * Connection to the database
         */
        val connection = Database.connect(JDBC_URL, DRIVER_CLASS_NAME, "root", "root")
    }
}