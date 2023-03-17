package com.schulverwaltung.mocks.repository

import com.schulverwaltung.dto.Secretary
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import org.jetbrains.exposed.sql.Transaction

class MockSecretaryRepository : ISecretaryRepository {
    private var mockTable = mutableListOf<Secretary>()
    var autoIncrementResetCount = 0
    override fun getAll(): List<Secretary> {
        return mockTable
    }

    override fun getById(id: Int): Secretary? {
        return mockTable.find {
            it.id == id
        }
    }

    override fun add(entity: Secretary): Secretary {
        var id = 1
        if (mockTable.isNotEmpty()) {
            mockTable.sortBy { it.id }
            id = mockTable.last().id
            id++
        }
        val tmpSecretary = Secretary(id, entity.firstName, entity.surName)
        mockTable.add(tmpSecretary)
        return tmpSecretary
    }

    override fun add(entities: List<Secretary>): List<Secretary> {
        val newList = mutableListOf<Secretary>()
        for (entity in entities) {
            newList.add(this.add(entity))
        }
        return newList
    }

    override fun update(entity: Secretary): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Secretary): Int {
        val removed = mockTable.remove(entity)
        return if (removed) 1 else 0
    }

    override fun deleteById(id: Int): Int {
        val removed = mockTable.removeIf { it.id == id }
        return if (removed) 1 else 0
    }

    override fun deleteAll(): Int {
        val length = mockTable.count()
        mockTable.clear()
        return length
    }

    override fun resetAutoIncrement(transaction: Transaction) {
        autoIncrementResetCount++
    }
}