package com.schulverwaltung.mocks.utils

import com.schulverwaltung.utils.interfaces.IPasswordHasher

class MockPasswordHasher : IPasswordHasher {
    override fun hashPassword(password: String): String = password
    override fun verifyPassword(password: String, hash: String): Boolean = true
}