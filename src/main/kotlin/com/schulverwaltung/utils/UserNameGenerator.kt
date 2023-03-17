package com.schulverwaltung.utils

import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IUserNameGenerator
import java.time.format.DateTimeParseException
import kotlin.jvm.Throws

/**
 * UserNameGenerator
 * @property birthdayParser
 */
class UserNameGenerator(private val birthdayParser: IBirthdayParser) : IUserNameGenerator {
    /**
     * Concatenates the first three characters of the firstName and the lastName as well as the date in the format "yyyyMMdd"
     *
     * FirstName: "John",
     * LastName: "Weydenterm",
     * Birthday: "20.2.1998"
     *
     * Results in: JohWey19980220
     *
     * @param firstName of the user
     * @param lastName of the user
     * @param birthday as String of the user
     * @return the concatenated name of the user
     * @throws DateTimeParseException
     */
    @Throws(DateTimeParseException::class)
    override fun getUsername(firstName: String, lastName: String, birthday: String): String {
        val newBirthday = birthdayParser.parse(birthday)
        return firstName.substring(0, 3) + lastName.substring(0, 3) + newBirthday
    }
}