# susu
susu is a lightweight tool that that maps data from spreadsheets to Kotlin classes.

### Basic Use
Annotate the fields in your Kotlin class with the `@CellInfo` annotation, specifying the column to map data from.

##### Example
Let's say you have a spreadsheet that looks like this:

| id | name | age | lives in Toronto |
|---|---|---|---|
| 1 | Mark | 37 | TRUE |
| 2 | Matt | 35 | TRUE |
| 3 | Mike | 35 | FALSE |
| 4 | Alex | 27 | TRUE |

...and we want to parse this data into a list of `Person` data classes.

We can create our data class in Kotlin, as we would normally, and just add the `@CellInfo` annotation.

```kotlin
import annotation.CellInfo

data class Person(
    @CellInfo(index = 0) val id: Int = -1,
    @CellInfo(index = 1) val name: String = "",
    @CellInfo(index = 2) val age: Int = -1,
    @CellInfo(index = 3) val inToronto: Boolean = true,
)
```
It's important to note that, for susu to work, there must be a zero argument constructor available for the class. In our example, we do this by providing default values for all the Data Class fields.

Once annotated, the data can be parsed as follows:
```kotlin
val sheetDescriptor: SheetDescriptor<Person> = SheetDescriptor(Person::class.java).setHasHeader(true)
val rows: List<Person> = readSheet(pathToExcelSheet, sheetDescriptor)
```
This will return a list of populated 'Person' objects.
```
Person(id=1, name=Mark, age=37, inToronto=true)
Person(id=2, name=Matt, age=35, inToronto=true)
Person(id=3, name=Mike, age=35, inToronto=false)
Person(id=4, name=Alex, age=27, inToronto=true)
```

### Custom Mappings
There might be times where we need a custom parcer for data in our spreadsheet.

Let's take the example from above, but add `body fat` as a possible field.

| id | name | age | lives in Toronto | body fat |
|---|---|---|---|---|
| 1 | Mark | 37 | TRUE | 28.28% |
| 2 | Matt | 35 | TRUE | 18.18% |
| 3 | Mike | 35 | FALSE | 8.00% |
| 4 | Alex | 27 | TRUE |   |

We can create a custom parser that reads the value in the 5th column and transforms it to a Double, rounded to 1 decimal place.

Our parcer would look something like this:
```kotlin
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
```
...and to ensure susu uses this parcer for the field, we would update our Data Class to looks like this:

```kotlin
data class PersonWithBodyFat(
    @CellInfo(index = 0) val id: Int = -1,
    @CellInfo(index = 1) val name: String = "",
    @CellInfo(index = 2) val age: Int = -1,
    @CellInfo(index = 3) val inToronto: Boolean = true,
    @CellInfo(index = 4, cellParser = CellPercentageParser::class) val bodyFat: Double = 0.0,
)
```
Running the tool in the same way we did previously, we would get an output:
```
PersonWithBodyFat(id=1, name=Mark, age=37, inToronto=true, bodyFat=28.8)
PersonWithBodyFat(id=2, name=Matt, age=35, inToronto=true, bodyFat=18.2)
PersonWithBodyFat(id=3, name=Mike, age=35, inToronto=false, bodyFat=8.0)
PersonWithBodyFat(id=4, name=Alex, age=27, inToronto=true, bodyFat=0.0)
```

### Maintenance
This project is maintained by [Mark Iantorno][Link-markGithub].

[Link-markGithub]: https://github.com/markiantorno

