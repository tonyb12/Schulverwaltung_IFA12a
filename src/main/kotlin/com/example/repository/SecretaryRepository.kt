package com.example.repository

import com.example.database.objects.Secretaries
import com.example.repository.interfaces.ISecretaryRepository
import com.example.model.Secretary
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SecretaryRepository : ISecretaryRepository {
    private val connection: Database
    constructor(connection: Database) {
        this.connection = connection
    }

    override fun getAll(): List<Secretary> {
        return transaction(connection) {
            Secretaries.selectAll().map { Secretary.fromRow(it) }
        }
    }

    override fun getById(id: Int): Secretary? {
        return transaction(connection) {
            val secretary = Secretaries.select { Secretaries.id eq id }.singleOrNull() ?: return@transaction null
            Secretary.fromRow(secretary)
        }
    }

    override fun add(entity: Secretary): Secretary {
        return transaction(connection) {
            val id = Secretaries.insert {
                it[Secretaries.firstName] = entity.firstName
                it[Secretaries.surName] = entity.surName
            } get Secretaries.id
            Secretary.fromRow(Secretaries.select { Secretaries.id eq id }.single())
        }
    }

    override fun update(entity: Secretary): Int {
        return transaction(connection) {
            Secretaries.update {
                it[Secretaries.firstName] = entity.firstName
                it[Secretaries.surName] = entity.surName
            }
        }
    }

    override fun delete(entity: Secretary): Int {
        return transaction(connection) {
            Secretaries.deleteAll()
        }
    }

    override fun deleteById(id: Int): Int {
        return transaction(connection) {
            Secretaries.deleteWhere{ Secretaries.id eq id }
        }
    }

    override fun deleteAll(): Int {
        return transaction(connection) {
            Secretaries.deleteAll()
        }
    }

}