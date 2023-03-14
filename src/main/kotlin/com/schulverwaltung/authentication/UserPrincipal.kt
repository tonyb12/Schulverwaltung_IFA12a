package com.schulverwaltung.authentication


import io.ktor.server.auth.*

class UserPrincipal(val userName: String, val type: UserType, val userId: Int) : Principal {
}