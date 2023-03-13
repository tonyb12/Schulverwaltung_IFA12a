package com.example.database.objects

import org.jetbrains.exposed.sql.Table
object SecretarySecrets: Table() {
    val id = integer("id").autoIncrement()
    val userName = char("userName", 14)
    val hash = char("hash", length = 65)
    val userId = integer("userId").uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}