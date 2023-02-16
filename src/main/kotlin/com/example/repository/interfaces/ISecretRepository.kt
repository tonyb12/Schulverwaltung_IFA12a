package com.example.repository.interfaces

import com.example.model.SecretarySecret

interface ISecretRepository<T> : IRepository<T, Int> {
    fun getByUserName(userName: String): T?
}