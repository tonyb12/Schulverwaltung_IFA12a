package com.example.controllers

import com.example.controllers.interfaces.ISecretController
import com.example.model.ISecret
import com.example.unitofwork.UnitOfWork

class StudentsSecretController : ISecretController {
    private val unitOfWork = UnitOfWork()

    override fun getByUserName(userName: String): ISecret? {
        
    }

    override fun getAll(): List<ISecret> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): ISecret? {
        TODO("Not yet implemented")
    }

    override suspend fun add(entity: ISecret): ISecret {
        TODO("Not yet implemented")
    }

    override fun add(entity: List<ISecret>): List<ISecret> {
        TODO("Not yet implemented")
    }

    override fun update(entity: ISecret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: ISecret): Int {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        TODO("Not yet implemented")
    }
}