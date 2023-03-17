package com.schulverwaltung.utils.interfaces

import java.time.format.DateTimeParseException
import kotlin.jvm.Throws

/**
 * BirthdayParser Interface
 */
interface IBirthdayParser {
    /**
     * Parses a date in the format "dd.MM.yyyy" to a String in the format "yyyyMMdd"
     *
     * @param date as String in the format "dd.MM.yyyy"
     * @return Date as a String in the format "yyyyMMdd"
     * @throws DateTimeParseException
     */
    @Throws(DateTimeParseException::class)
    fun parse(date: String): String
}