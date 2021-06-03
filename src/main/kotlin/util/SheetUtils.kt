package util

import SheetDescriptor
import annotation.CellInfo
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.full.createInstance

@Throws(IOException::class, InstantiationException::class, IllegalAccessException::class)
fun <R> readSheet(path: String, sheetDescriptor: SheetDescriptor<R>): List<R> {
    val excelFile = File(path)
    val result: MutableList<R> = ArrayList()
    FileInputStream(excelFile).use { fis ->
        XSSFWorkbook(fis).use { workbook ->
            // open specified sheet
            val sheet = workbook.getSheetAt(sheetDescriptor.sheetIndex)
            // Iterate over rows
            val rowIt: Iterator<Row> = sheet.iterator()
            var currentRowIndex = 0
            while (rowIt.hasNext()) {
                val row = rowIt.next()
                if (sheetDescriptor.firstDataRowIndex > currentRowIndex) {
                    currentRowIndex++
                    continue
                }
                val rowInstance: R = sheetDescriptor.rowClass.newInstance()
                rowInstance?.let {
                    // Iterate over row cells
                    var fieldDef: Field
                    val cellIterator = row.cellIterator()
                    while (cellIterator.hasNext()) {
                        val cell = cellIterator.next()
                        // check if there is mapping field for current cell
                        fieldDef = sheetDescriptor.fieldsMapper[cell.address.column]!!
                        fieldDef.getAnnotation(CellInfo::class.java).cellParser
                            .createInstance()
                            .parse(cell, it, fieldDef)
                    }
                    result.add(it)
                }
                currentRowIndex++
            }
        }
    }
    return result
}
