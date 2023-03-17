package com.schulverwaltung.controller.interfaces

/**
 * Base interface for all controller
 *
 * @param T Type of the return value
 * @param K Type of the id
 */
interface IController<T, K> {
    /**
     * Get a list of T from the repository
     *
     * @return List of T
     */
    suspend fun getAll(): List<T>
    /**
     * Gets T by its id from the repository
     *
     * @param id of T
     * @return T
     */
    suspend fun getById(id: K): T?
    /**
     * Adds T to the repository
     *
     * @param entity T
     * @return T or null
     */
    suspend fun add(entity: T): T
    /**
     * Add a list of T to the repository
     *
     * @param entities List of CSVImportHistory
     * @return List of CSVImportHistory
     */
    suspend fun add(entities: List<T>): List<T>
    /**
     * Updates T in the repository
     *
     * @param entity T
     * @return 1 if change was successful otherwise 0
     */
    suspend fun update(entity: T): Int
    /**
     * Deletes T from the repository
     *
     * @param entity T
     * @return 1 if deletion was successful otherwise 0
     */
    suspend fun delete(entity: T): Int
    /**
     * Deletes T by its id from the repository
     *
     * @param id id of T
     * @return 1 if deletion was successful otherwise 0
     */
    suspend fun deleteById(id: K): Int
    /**
     * Deletes all entries from the repository
     *
     * @return An integer representing the number of deletions
     */
    suspend fun deleteAll(): Int
}