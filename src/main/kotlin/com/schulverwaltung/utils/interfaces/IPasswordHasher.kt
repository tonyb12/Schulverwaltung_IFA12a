package com.schulverwaltung.utils.interfaces

/**
 * PasswordHasher Interface
 */
interface IPasswordHasher {
    /**
     * Hash password
     *
     * @param password the password to hash
     * @return the hashed password
     */
    fun hashPassword(password: String): String

    /**
     * Verify password
     *
     * @param password password to verify
     * @param hash the previously hashed password
     * @return true if the passwords match, false otherwise
     */
    fun verifyPassword(password: String, hash: String): Boolean
}