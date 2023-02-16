package com.example.repository

import com.example.database.objects.SecretarySecrets
import com.example.model.Secret
import com.example.repository.interfaces.ISecretRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SecretRepository : ISecretRepository {
    private val connection: Database
    constructor(connection: Database) {
        this.connection = connection
    }

    override fun getByUserName(userName: String): Secret? {
        return transaction(connection) {
            val secretRow = SecretarySecrets.select { SecretarySecrets.userName eq userName }.singleOrNull() ?: return@transaction null
            Secret.fromRow(secretRow)
        }
    }

    override fun getAll(): List<Secret> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Secret? {
        TODO("Not yet implemented")
    }

    override fun add(entity: Secret): Secret {
        return transaction(connection) {
            val id = SecretarySecrets.insert {
                it[SecretarySecrets.userName] = entity.userName
                it[SecretarySecrets.hash] = entity.hash
            } get SecretarySecrets.id
            Secret.fromRow(SecretarySecrets.select { SecretarySecrets.id eq id }.single())
        }
    }

    override fun update(entity: Secret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Secret): Int {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        return transaction(connection) {
            SecretarySecrets.deleteAll()
        }
    }
}