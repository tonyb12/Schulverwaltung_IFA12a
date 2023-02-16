package com.example.database.objects

import org.jetbrains.exposed.sql.Table
object SecretarySecret: Table() {
    val id = integer("id").autoIncrement()
    
}