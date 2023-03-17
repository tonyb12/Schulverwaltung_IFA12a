package com.schulverwaltung.authentication


import io.ktor.server.auth.*

/**
 * Used during the authentication process in ktor
 * Inherits the base Principal add extends it with the basic UserInformation needed
 *
 * @property userName name of the user
 * @property type which type does the user represent.
 * @property userId id fetched from the table that stores the user along with its information
 * @constructor Create empty User principal
 */
class UserPrincipal(val userName: String, val type: UserType, val userId: Int) : Principal