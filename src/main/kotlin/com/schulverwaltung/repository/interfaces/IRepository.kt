package com.schulverwaltung.repository.interfaces

import org.jetbrains.exposed.sql.Transaction

interface IRepository<T, K> {
    /**
     * Selects all entries from the database
     *
     * @return List of T
     */
    fun getAll(): List<T>
    /**
     * Gets an entry by its id
     *
     * @param id of T
     * @return T or null
     */
    fun getById(id: K): T?
    /**
     * Inserts an entity into the table
     *
     * @param entity T
     * @return T
     */
    fun add(entity: T): T
    /**
     * Batch inserts a list of entities into the table
     *
     * @param entities List of T
     * @return List of T
     */
    fun add(entities: List<T>): List<T>
    /**
     * Updates an entry in the table
     *
     * @param entity Student
     * @return 1 if change was successful otherwise 0
     */
    fun update(entity: T): Int
    /**
     * Removes an entry from the table
     *
     * @param entity T
     * @return 1 if deletion was successful otherwise 0
     */
    fun delete(entity: T): Int
    /**
     * Removes an entry by its id from the table
     *
     * @param id of the T entry
     * @return 1 if deletion was successful otherwise 0
     */
    fun deleteById(id: K): Int
    /**
     * Deletes all entries from the table
     *
     * @return An integer representing the number of deletions
     */
    fun deleteAll(): Int
    /**
     * Resets the autoincrement property of the table
     *
     * @param transaction current transaction scope
     */
    fun resetAutoIncrement(transaction: Transaction)
}