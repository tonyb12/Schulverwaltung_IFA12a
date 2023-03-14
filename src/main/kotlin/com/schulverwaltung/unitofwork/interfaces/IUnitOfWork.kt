package com.schulverwaltung.unitofwork.interfaces

import com.schulverwaltung.repository.interfaces.ISecretRepository
import com.schulverwaltung.repository.interfaces.ISecretaryRepository
import com.schulverwaltung.repository.interfaces.IStudentRepository

interface IUnitOfWork {
    val secretaryRepository: ISecretaryRepository
    val studentRepository: IStudentRepository
    val secretarySecretRepository: ISecretRepository
    val studentSecretRepository: ISecretRepository

    fun commit()
    fun rollback()
}