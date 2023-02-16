package com.example.unitofwork.interfaces

import com.example.repository.interfaces.ISecretRepository
import com.example.repository.interfaces.ISecretaryRepository
import com.example.repository.interfaces.IStudentRepository

interface IUnitOfWork {
    val secretaryRepository: ISecretaryRepository
    val studentRepository: IStudentRepository
    val secretarySecretRepository: ISecretRepository
    val studentSecretRepository: ISecretRepository

    fun commit()
    fun rollback()
}