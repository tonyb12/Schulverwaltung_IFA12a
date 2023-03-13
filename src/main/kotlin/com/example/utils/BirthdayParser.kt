package com.example.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BirthdayParser {
    companion object {
        fun parse(date: String): String {
            val tmpDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            return tmpDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()
        }
    }
}