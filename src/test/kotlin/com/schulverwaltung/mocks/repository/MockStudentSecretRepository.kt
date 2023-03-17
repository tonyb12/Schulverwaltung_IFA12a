package com.schulverwaltung.mocks.repository

import com.schulverwaltung.dto.StudentSecret
import com.schulverwaltung.dto.interfaces.ISecret
import com.schulverwaltung.repository.interfaces.IStudentSecretRepository
import org.jetbrains.exposed.sql.Transaction

class MockStudentSecretRepository : IStudentSecretRepository {
    private var mockTable = mutableListOf<StudentSecret>()
    var autoIncrementResetCount = 0
    override fun getByUserName(userName: String): ISecret? {
        return mockTable.find { it.userName == userName }
    }

    override fun getAll(): List<ISecret> {
        return mockTable
    }

    override fun getById(id: Int): ISecret? {
        return mockTable.find {
            it.id == id
        }
    }

    override fun add(entity: ISecret): ISecret {
        var id = 1
        if (mockTable.isNotEmpty()) {
            mockTable.sortBy { it.id }
            id = mockTable.last().id
            id++
        }
        val tmpStudentSecret = StudentSecret(id, entity.userName, entity.hash, entity.userId)
        mockTable.add(tmpStudentSecret)
        return tmpStudentSecret
    }

    override fun add(entities: List<ISecret>): List<ISecret> {
        val newList = mutableListOf<ISecret>()
        for (entity in entities) {
            newList.add(this.add(entity))
        }
        return newList
    }

    override fun update(entity: ISecret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: ISecret): Int {
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