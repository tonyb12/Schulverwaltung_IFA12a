package com.example.repository

import com.example.database.objects.StudentSecrets
import com.example.dto.interfaces.ISecret
import com.example.dto.Secret
import com.example.repository.interfaces.ISecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentsSecretRepository : ISecretRepository {
    override fun getByUserName(userName: String): Secret? {
        val secretRow = StudentSecrets.select { StudentSecrets.userName eq userName }.singleOrNull() ?: return null
        return Secret.fromRow(secretRow)
    }

    override fun getAll(): List<Secret> {
        return StudentSecrets.selectAll().map { Secret.fromRow(it) }
    }

    override fun getById(id: Int): Secret? {
        val secret = StudentSecrets.select { StudentSecrets.id eq id }.singleOrNull() ?: return null
        return Secret.fromRow(secret)
    }

    override fun add(entity: ISecret): ISecret {
        val id = StudentSecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
        } get StudentSecrets.id
        return Secret.fromRow(StudentSecrets.select { StudentSecrets.id eq id }.single())
    }

    override fun add(entities: List<ISecret>): List<ISecret> {
        return StudentSecrets.batchInsert(entities) {
            this[StudentSecrets.userName] = it.hash
            this[StudentSecrets.hash] = it.hash
        }.toList().map { Secret.fromRow(it) }
    }

    override fun update(entity: ISecret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: ISecret): Int {
        return StudentSecrets.deleteWhere {id eq entity.id }
    }

    override fun deleteById(id: Int): Int {
        return StudentSecrets.deleteWhere { StudentSecrets.id eq id }
    }

    override fun deleteAll(): Int {
        return StudentSecrets.deleteAll()
    }
}