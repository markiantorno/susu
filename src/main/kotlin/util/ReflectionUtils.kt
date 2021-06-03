package util

import java.lang.reflect.Field

@Throws(IllegalAccessException::class, IllegalArgumentException::class)
fun setField(obj: Any, field: Field, value: Any) {
    makeAccessible(field)
    field.set(obj, value)
}

fun executeOnAllClassFields(clazz: Class<*>, processField: (fieldDef: Field) -> Unit) {
    for (field in clazz.declaredFields) {
        makeAccessible(field)
        processField(field)
    }
}

/**
 * Execute action on class fields that are annotated with the given annotation
 *
 * @param clazz
 * @param annotationClass
 * @param fieldCommand
 */
fun executeOnClassFieldsByAnnotation(
    clazz: Class<*>,
    annotationClass: Class<out Annotation>,
    fieldCommand: (Field) -> Field?
) {
    executeOnAllClassFields(clazz) { fieldDef: Field ->
        if (fieldDef.isAnnotationPresent(annotationClass)) fieldCommand(fieldDef)
    }
}

/**
 * Changes the accessibility of a passed in [Field] to allow us to modify its value.
 */
fun makeAccessible(field: Field?) {
    field?.let {
        it.isAccessible = true
    }
}
