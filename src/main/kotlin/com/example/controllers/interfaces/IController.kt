package com.example.controllers.interfaces

import com.example.unitofwork.UnitOfWork

interface IController<T,K> {
    fun getAll(): List<T>
    fun getById(id: K): T?
    fun add(entity: T): T
    fun add(entity: List<T>): List<T>
    fun update(entity: T): Int
    fun delete(entity: T): Int
    fun deleteById(id: K): Int
    fun deleteAll(): Int
}