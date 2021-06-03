package parser

import org.apache.poi.ss.usermodel.Cell
import java.lang.reflect.Field
import java.util.*

/**
 * Default cell parser used when no other parser is provided.
 *
 * Handles the following types:
 * * [String]
 * * [Int]
 * * [Long]
 * * [Double]
 * * [Boolean]
 * * [Date]
 *
 * ...using the 'out of the box' parsing tools provided in [Cell].
 */
class DefaultCellParser : CellParser() {
    override fun parse(cell: Cell, obj: Any, field: Field) {
        val cellValue: Any
        cellValue =
            when (field.type) {
                String::class.java -> cell.stringCellValue
                Int::class.javaPrimitiveType, Int::class.java -> java.lang.Double.valueOf(cell.numericCellValue).toInt()
                Long::class.javaPrimitiveType, Long::class.java -> java.lang.Double.valueOf(cell.numericCellValue).toLong()
                Double::class.javaPrimitiveType, Double::class.java -> cell.numericCellValue
                Boolean::class.javaPrimitiveType, Boolean::class.java -> cell.booleanCellValue
                Date::class.java -> cell.dateCellValue
                else -> {
                    logFailure(cell, field)
                    return
                }
            }
        setField(cell, obj, field, cellValue)
    }
}