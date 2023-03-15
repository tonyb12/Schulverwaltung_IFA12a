package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IUserNameGenerator

class UserNameGenerator(private val birthdayParser: IBirthdayParser) : IUserNameGenerator {
    override fun getUsername(firstName: String, lastName: String, birthday: String): String {
        val newBirthday = birthdayParser.parse(birthday)
        return firstName.substring(0, 3) + lastName.substring(0, 3) + newBirthday
    }
}