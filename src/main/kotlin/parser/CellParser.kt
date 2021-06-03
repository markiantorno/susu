package parser

import org.apache.poi.ss.usermodel.Cell
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.reflect.Field
import kotlin.jvm.Throws

/**
 * Abstract class all custom cell parsers must extend.
 */
abstract class CellParser {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Base method called when parsing a given cell to populate an annotated field.
     *
     * To write your own cell parser, implement your logic here in your extended class.
     */
    abstract fun parse(cell: Cell, obj: Any, field: Field)

    /**
     * Set the value for the given field.
     * @param cell The [Cell] read from the spreadsheet. Only included here for debugging purposes.
     * @param obj Instance of the model class being populated. The class in which the annotated field is located.
     * @param field The [Field] being populated.
     * @param cellValue The value read from the [Cell] in the excel sheet.
     */
    @Throws(NumberFormatException::class, IllegalAccessException::class)
    protected fun setField(cell: Cell, obj: Any, field: Field, cellValue: Any?) {
        try {
            field[obj] = cellValue
        } catch (e: NumberFormatException) {
            logFailure(cell = cell, field = field, exception = e)
        } catch (e: IllegalAccessException) {
            logFailure(cell = cell, field = field, exception = e)
        }
    }

    /**
     * Output failed cell operation, using the class SLF4J logger.
     */
    protected fun logFailure(cell: Cell, field: Field, exception: Exception = Exception()) {
        logger.error(
            "{} {} while parsing cell {} to field {}",
            this.javaClass,
            exception.message,
            cell.address.toString(),
            field.name
        )
    }
}


