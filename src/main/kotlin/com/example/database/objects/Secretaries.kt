package com.example.database.objects

import org.jetbrains.exposed.sql.Table
object Secretaries : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("firstName",1024)
    val surName = varchar("surName", 1024)

    override val primaryKey = PrimaryKey(id)
}