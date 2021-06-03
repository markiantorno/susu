import annotation.CellInfo
import util.executeOnClassFieldsByAnnotation
import java.lang.reflect.Field
import java.util.HashMap

class SheetDescriptor<R>(val rowClass: Class<R>) {
    var sheetIndex = 0
    var hasHeader = false
    var firstDataRowIndex = 0
    var fieldsMapper: MutableMap<Int, Field> = HashMap()

    fun setHasHeader(hasHeader: Boolean): SheetDescriptor<R> {
        this.hasHeader = hasHeader
        if (hasHeader && firstDataRowIndex == 0) firstDataRowIndex = 1
        return this
    }

    fun setFirstDataRowIndex(firstDataRowIndex: Int): SheetDescriptor<R> {
        this.firstDataRowIndex = firstDataRowIndex
        return this
    }

    init {
        executeOnClassFieldsByAnnotation(rowClass, CellInfo::class.java) { fieldDef ->
            val cellInfo: CellInfo = fieldDef.getAnnotation(CellInfo::class.java)
            fieldsMapper.put(cellInfo.index, fieldDef)
        }
    }
}