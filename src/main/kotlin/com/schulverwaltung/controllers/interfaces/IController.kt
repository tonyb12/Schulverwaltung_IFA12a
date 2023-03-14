package com.schulverwaltung.controllers.interfaces

interface IController<T,K> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: K): T?
    suspend fun add(entity: T): T
    suspend fun add(entities: List<T>): List<T>
    suspend fun update(entity: T): Int
    suspend fun delete(entity: T): Int
    suspend fun deleteById(id: K): Int
    suspend fun deleteAll(): Int
}