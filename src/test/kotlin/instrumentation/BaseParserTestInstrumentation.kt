package instrumentation

import model.Person

object BaseParserTestInstrumentation {

    fun givenAPathToAnExcelSheetDescribingPeople(): String {
        return javaClass.classLoader.getResource("test_sheet_basic.xlsx")!!.path
    }

    fun givenAListOfExpectedPersons(): List<Person> {
        return listOf(
            Person(id = 1, name = "Mark", age = 37, inToronto = true),
            Person(id = 2, name = "Matt", age = 35, inToronto = true),
            Person(id = 3, name = "Mike", age = 35, inToronto = false),
            Person(id = 4, name = "Alex", age = 27, inToronto = true),
        )
    }
}