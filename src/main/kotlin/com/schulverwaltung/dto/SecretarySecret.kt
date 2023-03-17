package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.SecretarySecrets
import com.schulverwaltung.database.objects.StudentSecrets
import com.schulverwaltung.dto.interfaces.ISecret
import org.jetbrains.exposed.sql.ResultRow

/**
 * Secretary secret
 *
 * @property id
 * @property userName
 * @property hash
 * @property userId
 */
data class SecretarySecret(
    override val id: Int,
    override val userName: String,
    override val hash: String,
    override val userId: Int
) : ISecret {
    companion object {
        /**
         * Converts a ResultRow from the database to a secretary secret
         *
         * @param row that exposed returns
         * @return SecretarySecret object
         */
        fun fromRow(row: ResultRow): ISecret = SecretarySecret(
            id = row[SecretarySecrets.id],
            userName = row[SecretarySecrets.userName],
            hash = row[SecretarySecrets.hash],
            userId = row[SecretarySecrets.userId].value
        )
    }
}

/**
 * Student secret
 *
 * @property id
 * @property userName
 * @property hash
 * @property userId
 */
data class StudentSecret(
    override val id: Int,
    override val userName: String,
    override val hash: String,
    override val userId: Int
) : ISecret {
    companion object {
        /**
         * Converts a ResultRow from the database to a student secret
         *
         * @param row that exposed returns
         * @return StudentSecret object
         */
        fun fromRow(row: ResultRow): ISecret = StudentSecret(
            id = row[StudentSecrets.id],
            userName = row[StudentSecrets.userName],
            hash = row[StudentSecrets.hash],
            userId = row[StudentSecrets.userId].value
        )
    }
}
