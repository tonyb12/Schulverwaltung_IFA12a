package com.schulverwaltung.database.objects

import org.jetbrains.exposed.sql.Table

/**
 * Table definition for CsvImportHistories
 *
 */
object CsvImportHistories : Table() {

    val id = integer("id").autoIncrement()
    val uploadTime = varchar("upload_time", 1024)
    val fileName = varchar("file_name", 1024)

    override val primaryKey = PrimaryKey(id)

}