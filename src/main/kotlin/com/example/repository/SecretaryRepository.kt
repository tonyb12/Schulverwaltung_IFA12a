package com.example.repository

import com.example.database.objects.Secretaries
import com.example.repository.interfaces.ISecretaryRepository
import com.example.dto.Secretary
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SecretaryRepository : ISecretaryRepository {

    override fun getAll(): List<Secretary> {
        return Secretaries.selectAll().map { Secretary.fromRow(it) }
    }

    override fun getById(id: Int): Secretary? {
        val secretary = Secretaries.select { Secretaries.id eq id }.singleOrNull() ?: return null
        return Secretary.fromRow(secretary)
    }

    override fun add(entity: Secretary): Secretary {
        val id = Secretaries.insert {
            it[firstName] = entity.firstName
            it[surName] = entity.surName
        } get Secretaries.id
        return Secretary.fromRow(Secretaries.select { Secretaries.id eq id }.single())
    }

    override fun add(entities: List<Secretary>): List<Secretary> {
        return Secretaries.batchInsert(entities) {
            this[Secretaries.firstName] = it.firstName
            this[Secretaries.surName] = it.surName
        }.toList().map { Secretary.fromRow(it) }
    }

    override fun update(entity: Secretary): Int {
        return Secretaries.update {
                it[firstName] = entity.firstName
                it[surName] = entity.surName
        }
    }

    override fun delete(entity: Secretary): Int {
        return Secretaries.deleteAll()
    }

    override fun deleteById(id: Int): Int {
        return Secretaries.deleteWhere{ Secretaries.id eq id }
    }

    override fun deleteAll(): Int {
        return Secretaries.deleteAll()
    }
}