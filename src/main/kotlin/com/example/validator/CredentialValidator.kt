package com.example.validator

import com.example.controllers.interfaces.ISecretController
import com.example.dto.interfaces.ISecret
import org.mindrot.jbcrypt.BCrypt

class CredentialValidator {
    private val _controller: ISecretController
    constructor(controller: ISecretController) {
        this._controller = controller
    }
    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun verifyPassword(password: String, secretRow: ISecret): Boolean {
        return BCrypt.checkpw(password, secretRow.hash)
    }

    suspend fun verifyCredentials(userName: String, password: String): Boolean {
        val secretRow = _controller.getByUserName(userName) ?: return false
        return verifyPassword(password, secretRow)
    }
}