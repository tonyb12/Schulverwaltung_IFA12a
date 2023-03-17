package com.schulverwaltung.database.objects

import org.jetbrains.exposed.sql.Table
/**
 * Table definition for StudentSecrets
 *
 */
object StudentSecrets : Table() {
    val id = integer("id").autoIncrement()
    val userName = char("userName", 14)
    val hash = char("hash", length = 65)
    val userId = reference("userId", Students)

    override val primaryKey = PrimaryKey(id)
}