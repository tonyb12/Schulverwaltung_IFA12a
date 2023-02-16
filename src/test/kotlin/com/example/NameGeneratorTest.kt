package com.example

import com.example.utils.UserNameGenerator
import org.junit.Test
import kotlin.test.assertEquals

class NameGeneratorTest {


    @Test
    fun testGetUserName(){

        assertEquals("ToaBui19981212", UserNameGenerator.getUsername("Toan","Bui","12.12.1998"))

    }

}