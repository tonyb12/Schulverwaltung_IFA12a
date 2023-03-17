package com.schulverwaltung.repository

import com.schulverwaltung.database.objects.Secretaries
import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
/**
 * SecretaryRepository acts as an adapter between the controller and database.
 * It contains the logic to access the database and parses the data fetched from the database to an application wide standard
 *
 */
class SecretaryRepository : ISecretaryRepository {
    /**
     * Selects all entries from the database
     *
     * @return List of Secretary
     */
    override fun getAll(): List<Secretary> {
        return Secretaries.selectAll().map { Secretary.fromRow(it) }
    }
    /**
     * Gets an entry by its id
     *
     * @param id of Secretary
     * @return Secretary or null
     */
    override fun getById(id: Int): Secretary? {
        val secretary = Secretaries.select { Secretaries.id eq id }.singleOrNull() ?: return null
        return Secretary.fromRow(secretary)
    }
    /**
     * Inserts an entity in the table
     *
     * @param entity Secretary
     * @return Secretary
     */
    override fun add(entity: Secretary): Secretary {
        val id = Secretaries.insert {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
        } get Secretaries.id
        return Secretary.fromRow(Secretaries.select { Secretaries.id eq id }.single())
    }
    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of Secretary
     * @return List of Secretary
     */
    override fun add(entities: List<Secretary>): List<Secretary> {
        return Secretaries.batchInsert(entities) {
            this[Secretaries.firstName] = it.firstName
            this[Secretaries.surName] = it.surName
        }.toList().map { Secretary.fromRow(it) }
    }
    /**
     * Updates an entry in the table
     *
     * @param entity Secretary
     * @return 1 if change was successful otherwise 0
     */
    override fun update(entity: Secretary): Int {
        return Secretaries.update({Secretaries.id eq entity.id}) {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
        }
    }
    /**
     * Removes an entry from the table
     *
     * @param entity Secretary
     * @return 1 if deletion was successful otherwise 0
     */
    override fun delete(entity: Secretary): Int {
        return Secretaries.deleteAll()
    }
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the Secretary entry
     * @return 1 if deletion was successful otherwise 0
     */
    override fun deleteById(id: Int): Int {
        return Secretaries.deleteWhere { Secretaries.id eq id }
    }
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    override fun deleteAll(): Int {
        return Secretaries.deleteAll()
    }
    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE Secretaries AUTO_INCREMENT = 1")
    }
}