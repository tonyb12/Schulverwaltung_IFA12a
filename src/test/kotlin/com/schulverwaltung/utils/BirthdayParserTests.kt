package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.time.format.DateTimeParseException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BirthdayParserTests : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<IBirthdayParser> { BirthdayParser() }
            }
        )
    }

    private val birthdayParser by inject<IBirthdayParser>()

    @Test
    fun `Should correctly parse the date`() {
        val date = "12.12.1998"
        assertEquals("19981212", birthdayParser.parse(date))
    }

    @Test
    fun `Should throw an error if the date is incorrect`() {
        // Should be in the format "dd.MM.yyyy"
        val date = "1.2.1999"
        assertFailsWith(DateTimeParseException::class, block = { birthdayParser.parse(date) })
    }
}

