package com.schulverwaltung.authentication

import io.ktor.server.auth.*

data class UserSession(val token: String) : Principal{

}