package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IPasswordHasher
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue

class PasswordHasherTests : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<IPasswordHasher> { PasswordHasher() }
            }
        )
    }

    private val passwordHasher by inject<IPasswordHasher>()

    @Test
    fun `Verify that hashing and afterwards verifying the same password works`() {
        val password = "j134890t4"
        val hash = passwordHasher.hashPassword(password)
        assertTrue(passwordHasher.verifyPassword(password, hash))
    }
}