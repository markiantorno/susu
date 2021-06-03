package instrumentation

import model.PersonWithBodyFat

object CustomParserTestInstrumentation {

    fun givenAPathToAnExcelSheetDescribingPeopleWithBodyFat(): String {
        return javaClass.classLoader.getResource("test_sheet_custom_parser.xlsx")!!.path
    }

    fun givenAListOfExpectedPersons(): List<PersonWithBodyFat> {
        return listOf(
            PersonWithBodyFat(id = 1, name = "Mark", age = 37, inToronto = true, bodyFat = 28.8),
            PersonWithBodyFat(id = 2, name = "Matt", age = 35, inToronto = true, bodyFat = 18.2),
            PersonWithBodyFat(id = 3, name = "Mike", age = 35, inToronto = false, bodyFat = 8.0),
            PersonWithBodyFat(id = 4, name = "Alex", age = 27, inToronto = true),
        )
    }
}