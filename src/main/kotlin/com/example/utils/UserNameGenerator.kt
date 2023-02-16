package com.example.utils

import com.example.dto.Student

class UserNameGenerator {

    companion object{

        fun getUsername( firstName:String,  lastName:String, birthday: String) : String{
            val newBirthday = birthday.substring(6,birthday.length) + birthday.substring(3,5) + birthday.substring(0,2)

            return firstName.substring(0,3) + lastName.substring(0,3) + newBirthday
        }


    }
}