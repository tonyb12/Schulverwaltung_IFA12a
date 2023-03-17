package com.schulverwaltung.authentication


/**
 * Information about the user for authorisation purposes.
 * Can mainly be used as mapping between the user, his name and his type (UserType).
 * Ideal usage is in a cache system.
 *
 * @property userName name of the user
 * @property type which type does the user represent.
 * @property userId id fetched from the table that stores the user along with its information
 * @constructor Create empty User info
 */
data class UserInfo(val userName: String, val type: UserType, val userId: Int)