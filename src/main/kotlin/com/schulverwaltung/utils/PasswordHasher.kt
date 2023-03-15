package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IPasswordHasher
import org.mindrot.jbcrypt.BCrypt

class PasswordHasher : IPasswordHasher {
    override fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun verifyPassword(password: String, hash: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}