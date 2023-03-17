package com.schulverwaltung.controller.interfaces

import com.schulverwaltung.dto.interfaces.ISecret

interface ISecretController : IController<ISecret, Int> {
    suspend fun getByUserName(userName: String): ISecret?
}

interface IStudentSecretController : ISecretController
interface ISecretarySecretController : ISecretController