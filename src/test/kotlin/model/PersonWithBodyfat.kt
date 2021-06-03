package model

import annotation.CellInfo
import parser.CellPercentageParser

data class PersonWithBodyFat(
    @CellInfo(index = 0) val id: Int = -1,
    @CellInfo(index = 1) val name: String = "",
    @CellInfo(index = 2) val age: Int = -1,
    @CellInfo(index = 3) val inToronto: Boolean = true,
    @CellInfo(index = 4, cellParser = CellPercentageParser::class) val bodyFat: Double = 0.0,
)
