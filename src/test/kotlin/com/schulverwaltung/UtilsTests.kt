package com.schulverwaltung

import com.schulverwaltung.utils.BirthdayParser
import com.schulverwaltung.utils.UserNameGenerator
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import kotlin.test.assertEquals

class UtilsTests : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<IBirthdayParser> {BirthdayParser()}
                single<IUserNameGenerator> {UserNameGenerator(get())}
            }
        )
    }
    @Test
    fun testGetUserName(){
        val userNameGenerator = get<IUserNameGenerator>()
        assertEquals("ToaBui19981212", userNameGenerator.getUsername("Toan","Bui","12.12.1998"))
    }
    @Test
    fun testBirthdayParsing(){
        val birthdayParser = get<IBirthdayParser>()
        assertEquals("19981212", birthdayParser.parse("12.12.1998"))
    }
}