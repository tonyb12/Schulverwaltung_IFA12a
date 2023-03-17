package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.Secretaries
import org.jetbrains.exposed.sql.ResultRow

/**
 * Secretary
 *
 * @property id
 * @property firstName
 * @property surName
 * @constructor Create empty Secretary
 */
data class Secretary(val id: Int, val firstName: String, val surName: String) {
    companion object {
        /**
         * Converts a ResultRow from the database to a secretary
         *
         * @param row that exposed returns
         * @return Secretary object
         */
        fun fromRow(row: ResultRow): Secretary = Secretary(
            id = row[Secretaries.id].value,
            firstName = row[Secretaries.firstName],
            surName = row[Secretaries.surName]
        )
    }
}