package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.time.format.DateTimeParseException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserNameGeneratorTests : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single<IBirthdayParser> { BirthdayParser() }
            single<IUserNameGenerator> { UserNameGenerator(get()) }
        })
    }

    private val userNameGenerator by inject<IUserNameGenerator>()

    @Test
    fun `Should correctly convert the information to a valid userName`() {
        val firstName = "Toan"
        val lastName = "Bui"
        val birthday = "12.12.1998"
        assertEquals("ToaBui19981212", userNameGenerator.getUsername(firstName, lastName, birthday))
    }

    @Test
    fun `Should throw an error if the date is not valid`() {
        val firstName = "Toan"
        val lastName = "Bui"
        val birthday = "Nonsense"
        assertFailsWith(
            DateTimeParseException::class,
            block = { userNameGenerator.getUsername(firstName, lastName, birthday) }
        )
    }
}