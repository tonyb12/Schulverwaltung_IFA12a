package com.schulverwaltung.database.objects

import org.jetbrains.exposed.dao.id.IntIdTable
/**
 * Table definition for Students
 *
 */
object Students : IntIdTable() {
    val firstName = varchar("firstName", 1024)
    val surName = varchar("surName", 1024)
    val className = varchar("className", 1024)
    val birthday = varchar("birthday", 1024)
    val email = varchar("email", 1024)
    val jobDesc = text("jobDesc")
}