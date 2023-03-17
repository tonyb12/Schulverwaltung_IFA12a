package com.schulverwaltung.authentication

import io.ktor.server.auth.*

/**
 * Holds the user session token created during the authentication process, which is then stored in a cookie.
 *
 * @property token
 * @constructor Create empty User session
 */
data class UserSession(val token: String) : Principal