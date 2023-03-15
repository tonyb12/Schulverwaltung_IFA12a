package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.Secretaries
import org.jetbrains.exposed.sql.ResultRow

data class Secretary(val id: Int, val firstName: String, val surName: String) {
    companion object {
        fun fromRow(row: ResultRow): Secretary = Secretary(
            id = row[Secretaries.id].value,
            firstName = row[Secretaries.firstName],
            surName = row[Secretaries.surName]
        )
    }
}