package com.schulverwaltung.repository.interfaces

import org.jetbrains.exposed.sql.Transaction

interface IRepository<T, K> {
    fun getAll(): List<T>
    fun getById(id: K): T?
    fun add(entity: T): T
    fun add(entities: List<T>): List<T>
    fun update(entity: T): Int
    fun delete(entity: T): Int
    fun deleteById(id: K): Int
    fun deleteAll(): Int
    fun resetAutoIncrement(transaction: Transaction)
}