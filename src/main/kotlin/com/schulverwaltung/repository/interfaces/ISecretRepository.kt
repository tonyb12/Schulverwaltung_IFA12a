package com.schulverwaltung.repository.interfaces

import com.schulverwaltung.dto.interfaces.ISecret

interface ISecretRepository : IRepository<ISecret, Int> {
    fun getByUserName(userName: String): ISecret?
}

interface IStudentSecretRepository : ISecretRepository
interface ISecretarySecretRepository : ISecretRepository