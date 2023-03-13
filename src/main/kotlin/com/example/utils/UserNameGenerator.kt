package com.example.utils

class UserNameGenerator {
    companion object{
        fun getUsername( firstName:String,  lastName:String, birthday: String) : String{
            val newBirthday = BirthdayParser.parse(birthday)
            return firstName.substring(0,3) + lastName.substring(0,3) + newBirthday
        }
    }
}