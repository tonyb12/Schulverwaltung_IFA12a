package com.example.repository

import com.example.database.objects.SecretarySecrets
import com.example.dto.interfaces.ISecret
import com.example.dto.Secret
import com.example.repository.interfaces.ISecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SecretarySecretRepository : ISecretRepository {
    override fun getByUserName(userName: String): Secret? {
        val secretRow = SecretarySecrets.select { SecretarySecrets.userName eq userName }.singleOrNull() ?: return null
        return Secret.fromRow(secretRow)
    }

    override fun getAll(): List<Secret> {
        return SecretarySecrets.selectAll().map { Secret.fromRow(it) }
    }

    override fun getById(id: Int): Secret? {
        val secret = SecretarySecrets.select { SecretarySecrets.id eq id }.singleOrNull() ?: return null
        return Secret.fromRow(secret)
    }

    override fun add(entity: ISecret): ISecret {
        val id = SecretarySecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
        } get SecretarySecrets.id
        return Secret.fromRow(SecretarySecrets.select { SecretarySecrets.id eq id }.single())
    }

    override fun add(entities: List<ISecret>): List<ISecret> {
        return SecretarySecrets.batchInsert(entities) {
            this[SecretarySecrets.userName] = it.hash
            this[SecretarySecrets.hash] = it.hash
        }.toList().map { Secret.fromRow(it) }
    }

    override fun update(entity: ISecret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: ISecret): Int {
        return SecretarySecrets.deleteWhere { id eq entity.id }
    }

    override fun deleteById(id: Int): Int {
        return SecretarySecrets.deleteWhere { SecretarySecrets.id eq id }
    }

    override fun deleteAll(): Int {
        return SecretarySecrets.deleteAll()
    }
}