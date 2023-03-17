package com.schulverwaltung.mocks.utils

import com.schulverwaltung.dto.Student
import com.schulverwaltung.utils.CsvReader
import org.junit.Test
import org.koin.test.KoinTest
import java.io.File
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class CsvReaderTests : KoinTest {
    @Test
    fun `Should correctly read and parse the csv`() {
        val fileInputStream = File("src/test/resources/studentsExport.csv").inputStream()
        val parsedData = CsvReader.readStudentCsv(fileInputStream)
        assertNotNull(parsedData)
        assertContains(
            parsedData, Student(0, "Toan", "Bui", "IFA12a", "12.12.1998", "tony.bui267@gmail.com", "Fachinformatiker - Anwendungsentwicklung")
        )
        assertContains(
            parsedData, Student(0, "Nico", "Hammon", "IFA12a", "22.05.2004", "hammon.nico@yahoo.com", "Fachinformatiker - Anwendungsentwicklung")
        )
        assertContains(
            parsedData, Student(0, "Fabian", "Schmidt", "IFA12a", "16.06.2000", "fabianschmidt.usan@gmx.de", "Fachinformatiker - Anwendungsentwicklung")
        )
        assertContains(
            parsedData, Student(0, "Christian", "Zahn", "IFA12a", "30.09.1994", "christianzahn94@gmx.de", "Fachinformatiker - Anwendungsentwicklung")
        )
    }
}