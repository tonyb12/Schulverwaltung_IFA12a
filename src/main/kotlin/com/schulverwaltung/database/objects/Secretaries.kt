package com.schulverwaltung.database.objects

import org.jetbrains.exposed.dao.id.IntIdTable
/**
 * Table definition for Secretaries
 *
 */
object Secretaries : IntIdTable() {
    val firstName = varchar("firstName", 1024)
    val surName = varchar("surName", 1024)
}