package com.schulverwaltung

import com.schulverwaltung.utils.BirthdayParser
import org.junit.Test
import kotlin.test.assertEquals

class BirthdayParserTest {
    @Test
    fun testParsing(){
        assertEquals("19981212", BirthdayParser.parse("12.12.1998"))
    }
}