package com.example.controllers

import com.example.controllers.interfaces.ISecretaryController
import com.example.model.Secretary
import com.example.unitofwork.UnitOfWork

class SecretaryController : ISecretaryController{
    private val _unitOfWork: UnitOfWork = UnitOfWork()
    override fun getAll(): List<Secretary> {
        return _unitOfWork.secretaryRepository.getAll()
    }

    override fun getById(id: Int): Secretary? {
        return _unitOfWork.secretaryRepository.getById(id)
    }

    override fun add(entity: Secretary): Secretary {
        return _unitOfWork.secretaryRepository.add(entity)
    }

    override fun add(entity: List<Secretary>): List<Secretary> {
        TODO("Not yet implemented")
    }

    override fun update(entity: Secretary): Int {
        return _unitOfWork.secretaryRepository.update(entity)
    }

    override fun delete(entity: Secretary): Int {
        return _unitOfWork.secretaryRepository.delete(entity)
    }

    override fun deleteById(id: Int): Int {
        return _unitOfWork.secretaryRepository.deleteById(id)
    }

    override fun deleteAll(): Int {
        return _unitOfWork.secretaryRepository.deleteAll()
    }
}