package com.example.data

import com.example.model.Student
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream

class CSVTest {
    fun readCsv (inputStream: InputStream): List<Student> {
        return csvReader{delimiter = ';'}.readAllWithHeader(inputStream).map {
            Student(
                1,
                it["Rufname"] ?: "",
                it["Familienname"] ?: "",
                it["Klasse"] ?: "",
                it["Geburtsdatum"] ?: "",
                it["Schüler/in E-Mail"] ?: ""
            )
        }
    }
}