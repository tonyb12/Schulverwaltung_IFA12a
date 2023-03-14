package com.schulverwaltung.database.objects

import org.jetbrains.exposed.sql.Table

object Students: Table() {
        val id = integer("id").autoIncrement()
        val firstName = varchar("firstName",1024)
        val surName = varchar("surName", 1024)
        val className = varchar("className", 1024)
        val birthday = varchar("birthday", 1024)
        val email = varchar("email", 1024)

        override val primaryKey = PrimaryKey(id)
}