package com.example.utils

import com.example.dto.interfaces.ISecret
import org.mindrot.jbcrypt.BCrypt

class PasswordHasher {
    companion object {
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }
        fun verifyPassword(password: String, hash: String): Boolean {
            return BCrypt.checkpw(password, hash)
        }
    }
}