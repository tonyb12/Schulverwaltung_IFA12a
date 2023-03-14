package com.schulverwaltung.dto.interfaces

interface ISecret {
    val id: Int
    val userName: String
    val hash: String
    val userId: Int
}