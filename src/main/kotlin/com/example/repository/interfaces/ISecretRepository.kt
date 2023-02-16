package com.example.repository.interfaces

import com.example.dto.interfaces.ISecret

interface ISecretRepository : IRepository<ISecret, Int> {
    fun getByUserName(userName: String): ISecret?
}