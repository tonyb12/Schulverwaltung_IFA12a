package com.schulverwaltung

class MultiException : Exception {
    val exceptionList: List<Exception>
    override val message: String
    constructor(exceptionList: List<Exception>) {
        this.exceptionList = exceptionList
        val tmp =  exceptionList.map { it }.toString()
        this.message = tmp
    }
}