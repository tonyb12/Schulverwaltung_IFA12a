package com.schulverwaltung.utils

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.schulverwaltung.dto.Student
import java.io.InputStream

class CsvReader {

    companion object {

        fun readStudentCsv(inputStream: InputStream): List<Student> {
            return csvReader { delimiter = ';' }.readAllWithHeader(inputStream).map {
                Student(
                    0,
                    it["Rufname"] ?: "",
                    it["Familienname"] ?: "",
                    it["Klasse"] ?: "",
                    it["Geburtsdatum"] ?: "",
                    it["Schüler/in E-Mail"] ?: "",
                    it["Ausbild. Ausbildungsberuf (Langbez. männl.)"] ?: ""
                )
            }
        }
    }
}