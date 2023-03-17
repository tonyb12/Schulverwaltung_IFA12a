package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser
import java.text.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.jvm.Throws

/**
 * Birthday parser
 */
class BirthdayParser : IBirthdayParser {
    /**
     * Parses a date in the format "dd.MM.yyyy" to a String in the format "yyyyMMdd"
     *
     * @param date as String in the format "dd.MM.yyyy"
     * @return Date as a String in the format "yyyyMMdd"
     * @throws DateTimeParseException
     */
    @Throws(DateTimeParseException::class)
    override fun parse(date: String): String {
        val tmpDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        return tmpDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()
    }
}