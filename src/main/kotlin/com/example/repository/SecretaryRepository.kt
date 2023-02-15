package com.example.repository

import com.example.repository.interfaces.ISecretaryRepository
import com.example.model.Secretary
import org.jetbrains.exposed.sql.Database

class SecretaryRepository : ISecretaryRepository {
    private val connection: Database
    constructor(connection: Database) {
        this.connection = connection
    }

    override fun getAll(): List<Secretary> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Secretary? {
        TODO("Not yet implemented")
    }

    override fun add(entity: Secretary): Secretary {
        TODO("Not yet implemented")
    }

    override fun update(entity: Secretary): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Secretary): Int {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

}