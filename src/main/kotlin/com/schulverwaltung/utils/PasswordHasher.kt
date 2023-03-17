package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IPasswordHasher
import org.mindrot.jbcrypt.BCrypt

/**
 * PasswordHasher
 */
class PasswordHasher : IPasswordHasher {
    /**
     * Hash password
     *
     * @param password the password to hash
     * @return the hashed password
     */
    override fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    /**
     * Check that a plaintext password matches a previously hashed one
     *
     * @param password to verify
     * @param hash to check against
     * @return true if the passwords match, false otherwise
     */
    override fun verifyPassword(password: String, hash: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}