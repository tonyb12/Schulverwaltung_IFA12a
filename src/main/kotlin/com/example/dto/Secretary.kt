package com.example.dto

import org.jetbrains.exposed.sql.ResultRow
import com.example.database.objects.Secretaries
data class Secretary(val id: Int, val firstName: String, val surName: String){
    companion object {
        fun fromRow(row: ResultRow): Secretary = Secretary(
            id = row[Secretaries.id],
            firstName = row[Secretaries.firstName],
            surName = row[Secretaries.surName]
        )
    }
}