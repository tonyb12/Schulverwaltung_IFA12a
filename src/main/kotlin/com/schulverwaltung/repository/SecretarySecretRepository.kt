package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.SecretarySecrets
import com.schulverwaltung.dto.SecretarySecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.repository.interfaces.ISecretarySecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SecretarySecretRepository : ISecretarySecretRepository {
    /**
     * Gets an entry by its username
     *
     * @param userName Name of the user
     * @return SecretarySecret or null
     */
    override fun getByUserName(userName: String): ISecret? {
        val secretRow = SecretarySecrets.select { SecretarySecrets.userName eq userName }.singleOrNull() ?: return null
        return SecretarySecret.fromRow(secretRow)
    }

    /**
     * Selects all entries from the database
     *
     * @return List of SecretarySecret
     */
    override fun getAll(): List<ISecret> {
        return SecretarySecrets.selectAll().map { SecretarySecret.fromRow(it) }
    }

    /**
     * Gets an entry by its id
     *
     * @param id of SecretarySecret
     * @return SecretarySecret or null
     */
    override fun getById(id: Int): ISecret? {
        val secret = SecretarySecrets.select { SecretarySecrets.id eq id }.singleOrNull() ?: return null
        return SecretarySecret.fromRow(secret)
    }

    /**
     * Inserts an entity in the table
     *
     * @param entity SecretarySecret
     * @return SecretarySecret
     */
    override fun add(entity: ISecret): ISecret {
        val id = SecretarySecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        } get SecretarySecrets.id
        return SecretarySecret.fromRow(SecretarySecrets.select { SecretarySecrets.id eq id }.single())
    }

    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of SecretarySecret
     * @return List of SecretarySecret
     */
    override fun add(entities: List<ISecret>): List<ISecret> {
        return SecretarySecrets.batchInsert(entities) {
            this[SecretarySecrets.userName] = it.hash
            this[SecretarySecrets.hash] = it.hash
            this[SecretarySecrets.userId] = it.userId
        }.toList().map { SecretarySecret.fromRow(it) }
    }

    /**
     * Updates an entry in the table
     *
     * @param entity SecretarySecret
     * @return 1 if change was successful otherwise 0
     */
    override fun update(entity: ISecret): Int {
        return SecretarySecrets.update({ SecretarySecrets.id eq entity.id }) {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        }
    }
    /**
     * Removes an entry from the table
     *
     * @param entity SecretarySecret
     * @return 1 if deletion was successful otherwise 0
     */
    override fun delete(entity: ISecret): Int {
        return SecretarySecrets.deleteWhere { id eq entity.id }
    }
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the SecretarySecret entry
     * @return 1 if deletion was successful otherwise 0
     */
    override fun deleteById(id: Int): Int {
        return SecretarySecrets.deleteWhere { SecretarySecrets.id eq id }
    }
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    override fun deleteAll(): Int {
        return SecretarySecrets.deleteAll()
    }
    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE SecretarySecrets AUTO_INCREMENT = 1")
    }
}