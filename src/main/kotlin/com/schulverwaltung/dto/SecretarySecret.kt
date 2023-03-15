package com.schulverwaltung.dto

import com.schulverwaltung.database.objects.SecretarySecrets
import com.schulverwaltung.database.objects.StudentSecrets
import com.schulverwaltung.dto.interfaces.ISecret
import org.jetbrains.exposed.sql.ResultRow

data class SecretarySecret(
    override val id: Int,
    override val userName: String,
    override val hash: String,
    override val userId: Int
) : ISecret {
    companion object {
        fun fromRow(row: ResultRow): ISecret = SecretarySecret(
            id = row[SecretarySecrets.id],
            userName = row[SecretarySecrets.userName],
            hash = row[SecretarySecrets.hash],
            userId = row[SecretarySecrets.userId]
        )
    }
}

data class StudentSecret(
    override val id: Int,
    override val userName: String,
    override val hash: String,
    override val userId: Int
) : ISecret {
    companion object {
        fun fromRow(row: ResultRow): ISecret = StudentSecret(
            id = row[StudentSecrets.id],
            userName = row[StudentSecrets.userName],
            hash = row[StudentSecrets.hash],
            userId = row[StudentSecrets.userId]
        )
    }
}
