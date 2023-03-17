package com.schulverwaltung

/**
 * Multi exception
 * Takes a list of exceptions and creates a message of the concatenated causes
 * The exceptionsList is still accessible from the outside
 *
 */
class MultiException : Exception {
    val exceptionList: List<Exception>
    override val message: String
    constructor(exceptionList: List<Exception>) {
        this.exceptionList = exceptionList
        val tmp =  exceptionList.map { it }.toString()
        this.message = tmp
    }
}