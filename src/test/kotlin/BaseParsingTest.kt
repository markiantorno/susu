import instrumentation.BaseParserTestInstrumentation.givenAListOfExpectedPersons
import instrumentation.BaseParserTestInstrumentation.givenAPathToAnExcelSheetDescribingPeople
import model.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import util.readSheet
import java.io.IOException

class BaseParsingTest {

    @Test
    fun `Test basic happy path usage of annotation`() {
        val path = givenAPathToAnExcelSheetDescribingPeople()
        val expectedOutcome = givenAListOfExpectedPersons()

        try {
            val sheetDescriptor: SheetDescriptor<Person> =
                SheetDescriptor(Person::class.java).setHasHeader(true)
            val rows: List<Person> = readSheet(path, sheetDescriptor)
            rows.forEach { f -> println(f.toString()) }
            Assertions.assertArrayEquals(expectedOutcome.toTypedArray(), rows.toTypedArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}