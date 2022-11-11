package strucut

import strucut.StrucutAssertionError.*

/**
 * This method cleans stacktrace from deeply nested methods used in struct validation
 * */
@Suppress("NOTHING_TO_INLINE")
@PublishedApi
internal inline fun StrucutAssertionError.clone(): StrucutAssertionError = when (this) {
    is ObjectAsProperty -> ObjectAsProperty(name, value)
    is ObjectInObjectMissing -> ObjectInObjectMissing(name, keys)
    is PropertyMissing -> PropertyMissing(name, keys)
    is ValueMismatch -> ValueMismatch(name, expected, actual)
}