package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.StudentSecrets
import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.repository.interfaces.IStudentSecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentSecretRepository : IStudentSecretRepository {
    /**
     * Gets an entry by its username
     *
     * @param userName Name of the user
     * @return StudentSecret or null
     */
    override fun getByUserName(userName: String): ISecret? {
        val secretRow = StudentSecrets.select { StudentSecrets.userName eq userName }.singleOrNull() ?: return null
        return StudentSecret.fromRow(secretRow)
    }

    /**
     * Selects all entries from the database
     *
     * @return List of StudentSecret
     */
    override fun getAll(): List<ISecret> {
        return StudentSecrets.selectAll().map { StudentSecret.fromRow(it) }
    }

    /**
     * Gets an entry by its id
     *
     * @param id of StudentSecret
     * @return StudentSecret or null
     */
    override fun getById(id: Int): ISecret? {
        val secret = StudentSecrets.select { StudentSecrets.id eq id }.singleOrNull() ?: return null
        return StudentSecret.fromRow(secret)
    }

    /**
     * Inserts an entity in the table
     *
     * @param entity StudentSecret
     * @return StudentSecret
     */
    override fun add(entity: ISecret): ISecret {
        val id = StudentSecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        } get StudentSecrets.id
        return StudentSecret.fromRow(StudentSecrets.select { StudentSecrets.id eq id }.single())
    }

    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of StudentSecret
     * @return List of StudentSecret
     */
    override fun add(entities: List<ISecret>): List<ISecret> {
        return StudentSecrets.batchInsert(entities) {
            this[StudentSecrets.userName] = it.userName
            this[StudentSecrets.hash] = it.hash
            this[StudentSecrets.userId] = it.userId
        }.toList().map { StudentSecret.fromRow(it) }
    }

    /**
     * Updates an entry in the table
     *
     * @param entity StudentSecret
     * @return 1 if change was successful otherwise 0
     */
    override fun update(entity: ISecret): Int {
        return StudentSecrets.update({ StudentSecrets.id eq entity.id }) {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        }
    }
    /**
     * Removes an entry from the table
     *
     * @param entity StudentSecret
     * @return 1 if deletion was successful otherwise 0
     */
    override fun delete(entity: ISecret): Int {
        return StudentSecrets.deleteWhere { id eq entity.id }
    }
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the StudentSecret entry
     * @return 1 if deletion was successful otherwise 0
     */
    override fun deleteById(id: Int): Int {
        return StudentSecrets.deleteWhere { StudentSecrets.id eq id }
    }
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    override fun deleteAll(): Int {
        return StudentSecrets.deleteAll()
    }
    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE StudentSecrets AUTO_INCREMENT = 1")
    }
}