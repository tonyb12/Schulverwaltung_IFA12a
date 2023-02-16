package com.example.dto

import com.example.database.objects.SecretarySecrets
import com.example.dto.interfaces.ISecret
import org.jetbrains.exposed.sql.ResultRow

data class Secret(override val id: Int, override val userName: String, override val hash: String) : ISecret {
    companion object {
        fun fromRow(row: ResultRow): Secret = Secret(
            id = row[SecretarySecrets.id],
            userName = row[SecretarySecrets.userName],
            hash = row[SecretarySecrets.hash]
        )
    }
}