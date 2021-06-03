import instrumentation.CustomParserTestInstrumentation.givenAListOfExpectedPersons
import instrumentation.CustomParserTestInstrumentation.givenAPathToAnExcelSheetDescribingPeopleWithBodyFat
import model.PersonWithBodyFat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import util.readSheet
import java.io.IOException

class CustomParserTests {

    @Test
    fun `Test basic happy path usage of annotation`() {
        val path = givenAPathToAnExcelSheetDescribingPeopleWithBodyFat()
        val expectedOutcome = givenAListOfExpectedPersons()

        try {
            val sheetDescriptor: SheetDescriptor<PersonWithBodyFat> =
                SheetDescriptor(PersonWithBodyFat::class.java).setHasHeader(true)
            val rows: List<PersonWithBodyFat> = readSheet(path, sheetDescriptor)
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