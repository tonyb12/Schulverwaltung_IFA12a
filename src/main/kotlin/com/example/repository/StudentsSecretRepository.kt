package com.example.repository

import com.example.database.objects.StudentSecrets
import com.example.dto.interfaces.ISecret
import com.example.dto.StudentSecret
import com.example.repository.interfaces.ISecretRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentsSecretRepository : ISecretRepository {
    override fun getByUserName(userName: String): ISecret? {
        val secretRow = StudentSecrets.select { StudentSecrets.userName eq userName }.singleOrNull() ?: return null
        return StudentSecret.fromRow(secretRow)
    }

    override fun getAll(): List<ISecret> {
        return StudentSecrets.selectAll().map { StudentSecret.fromRow(it) }
    }

    override fun getById(id: Int): ISecret? {
        val secret = StudentSecrets.select { StudentSecrets.id eq id }.singleOrNull() ?: return null
        return StudentSecret.fromRow(secret)
    }

    override fun add(entity: ISecret): ISecret {
        val id = StudentSecrets.insert {
            it[userName] = entity.userName
            it[hash] = entity.hash
            it[userId] = entity.userId
        } get StudentSecrets.id
        return StudentSecret.fromRow(StudentSecrets.select { StudentSecrets.id eq id }.single())
    }

    override fun add(entities: List<ISecret>): List<ISecret> {
        return StudentSecrets.batchInsert(entities) {
            this[StudentSecrets.userName] = it.userName
            this[StudentSecrets.hash] = it.hash
            this[StudentSecrets.userId] = it.userId
        }.toList().map { StudentSecret.fromRow(it) }
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

    override fun resetAutoIncrement(transaction: Transaction) {
        transaction.exec("ALTER TABLE StudentSecrets AUTO_INCREMENT = 1")
    }
}