package com.schulverwaltung.utils.interfaces

interface IUserNameGenerator {
    fun getUsername(firstName: String, lastName: String, birthday: String): String
}