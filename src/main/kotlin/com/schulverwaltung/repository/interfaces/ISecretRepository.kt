package com.schulverwaltung.repository.interfaces

import com.schulverwaltung.dto.interfaces.ISecret

interface ISecretRepository : IRepository<ISecret, Int> {
    /**
     * Gets an entry by its username
     *
     * @param userName Name of the user
     * @return ISecret or null
     */
    fun getByUserName(userName: String): ISecret?
}

interface IStudentSecretRepository : ISecretRepository
interface ISecretarySecretRepository : ISecretRepository