package parser

import org.apache.poi.ss.usermodel.Cell
import util.setField
import java.lang.reflect.Field
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Custom parser to convert percentage cell values into [Double] rounded to 1 decimal place.
 */
class CellPercentageParser : CellParser() {

    override fun parse(cell: Cell, obj: Any, field: Field) {
        try {
            setField(obj, field, (cell.numericCellValue * 100).roundTo(Companion.DECIMAL_PLACES))
        } catch (e: NumberFormatException) {
            logFailure(cell, field, e)
        } catch (e: IllegalAccessException) {
            logFailure(cell, field, e)
        }
    }

    companion object {
        const val DECIMAL_PLACES = 1
    }
}

/**
 * Extension function to round a given [Double] to a number of decimal places.
 *
 * @param numFractionDigits Number of decimal places to round to.
 */
fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}