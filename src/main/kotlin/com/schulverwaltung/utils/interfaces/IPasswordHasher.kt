package com.schulverwaltung.utils.interfaces

interface IPasswordHasher {
    fun hashPassword(password: String): String
    fun verifyPassword(password: String, hash: String): Boolean
}