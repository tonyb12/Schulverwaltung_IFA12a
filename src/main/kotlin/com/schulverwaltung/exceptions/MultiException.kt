package com.schulverwaltung.exceptions

/**
 * Multi exception
 * Takes a list of exceptions and creates a message of the concatenated causes
 * The exceptionsList is still accessible from the outside
 *
 */
class MultiException(val exceptionList: List<Exception>) : Exception() {
    override val message: String

    init {
        val tmp =  exceptionList.map { it }.toString()
        this.message = tmp
    }
}