package com.example.controllers

import com.example.controllers.interfaces.ISecretaryController
import com.example.model.Secretary
import com.example.unitofwork.UnitOfWork

class SecretaryController : ISecretaryController{
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override fun getAll(): List<Secretary> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Secretary? {
        TODO("Not yet implemented")
    }

    override fun add(entity: Secretary): Secretary {
        TODO("Not yet implemented")
    }

    override fun add(entity: List<Secretary>): List<Secretary> {
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