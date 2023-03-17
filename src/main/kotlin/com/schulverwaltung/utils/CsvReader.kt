package com.schulverwaltung.utils

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.schulverwaltung.dto.Student
import java.io.InputStream

/**
 * CsvReader
 * Provides several utility methods for converting the Csv
 *
 */
class CsvReader {

    companion object {

        /**
         * Reads a csv and converts it to a list of Students
         *
         * @param inputStream from a file or ...
         * @return List of Students
         */
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