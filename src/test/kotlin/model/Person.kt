package model

import annotation.CellInfo

data class Person(
    @CellInfo(index = 0) val id: Int = -1,
    @CellInfo(index = 1) val name: String = "",
    @CellInfo(index = 2) val age: Int = -1,
    @CellInfo(index = 3) val inToronto: Boolean = true,
)
