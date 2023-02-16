package com.example.controllers

import com.example.controllers.interfaces.ISecretController
import com.example.model.SecretarySecret
import com.example.unitofwork.UnitOfWork

class SecretController : ISecretController {
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override fun getByUserName(userName: String): SecretarySecret? {
        _unitOfWork.secretarySecretRepository
    }

    override fun getAll(): List<SecretarySecret> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): SecretarySecret? {
        TODO("Not yet implemented")
    }

    override fun add(entity: SecretarySecret): SecretarySecret {
        return _unitOfWork.secretarySecretRepository.add(entity)
    }

    override fun add(entity: List<SecretarySecret>): List<SecretarySecret> {
        TODO("Not yet implemented")
    }

    override fun update(entity: SecretarySecret): Int {
        TODO("Not yet implemented")
    }

    override fun delete(entity: SecretarySecret): Int {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        return _unitOfWork.secretarySecretRepository.deleteAll()
    }
}