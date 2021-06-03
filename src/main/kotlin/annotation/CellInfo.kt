package annotation

import parser.CellParser
import parser.DefaultCellParser
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CellInfo(val index: Int, val cellParser: KClass<out CellParser> = DefaultCellParser::class)
