package com.example.controllers.interfaces

import com.example.dto.interfaces.ISecret

interface ISecretController : IController<ISecret, Int> {
    suspend fun getByUserName(userName: String): ISecret?
}