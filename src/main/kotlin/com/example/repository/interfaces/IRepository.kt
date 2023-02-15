package com.example.database

interface IRepository <T,K>{
    fun read(): List<T>
    fun readById(id: K): T
    fun create(entity: T): T
    fun update(entity: T): T
    fun delete(entity: T): T
    fun deleteById(id: K): T

}