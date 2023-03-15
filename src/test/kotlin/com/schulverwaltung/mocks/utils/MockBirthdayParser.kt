package com.schulverwaltung.mocks.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser

class MockBirthdayParser : IBirthdayParser {
    override fun parse(date: String): String {
        return date
    }
}