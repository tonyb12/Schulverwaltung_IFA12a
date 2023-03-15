package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.SecretarySecrets
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.repository.interfaces.ISecretarySecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SecretarySecretRepository : ISecretarySecretRepository {
    override fun getByUserName(userName: String): ISecret? {
        val secretRow = SecretarySecrets.select { SecretarySecrets.userName eq userName }.singleOrNull() ?: return null
        return SecretarySecret.fromRow(secretRow)
    }

    override fun getAll(): List<ISecret> {
        return SecretarySecrets.selectAll().map { SecretarySecret.fromRow(it) }
    }

    override fun getById(id: Int): ISecret? {
        val secret = SecretarySecrets.select { SecretarySecrets.id eq id }.singleOrNull() ?: return null
        return SecretarySecret.fromRow(secret)
    }

    override fun add(entity: ISecret): ISecret {
        val id = SecretarySecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        } get SecretarySecrets.id
        return SecretarySecret.fromRow(SecretarySecrets.select { SecretarySecrets.id eq id }.single())
    }

    override fun add(entities: List<ISecret>): List<ISecret> {
        return SecretarySecrets.batchInsert(entities) {
            this[SecretarySecrets.userName] = it.hash
            this[SecretarySecrets.hash] = it.hash
            this[SecretarySecrets.userId] = it.userId
        }.toList().map { SecretarySecret.fromRow(it) }
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

    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE SecretarySecrets AUTO_INCREMENT = 1")
    }
}