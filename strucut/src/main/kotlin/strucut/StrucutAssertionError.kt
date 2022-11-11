package strucut

sealed class StrucutAssertionError(message: String) : AssertionError(message) {

    class ObjectInObjectMissing(val name: String, val keys: Iterable<String>) : StrucutAssertionError(
        "Expected object <$name> in <$keys>"
    )

    class PropertyMissing(val name: String, val keys: Iterable<String>) : StrucutAssertionError(
        "Expected property <$name> in <$keys>"
    )

    class ObjectAsProperty(val name: String, val value: Any?) : StrucutAssertionError(
        "Expected object <$name> but got <$value>"
    )

    class ValueMismatch(val name: String, val expected: Any?, val actual: Any?) : StrucutAssertionError(
        "Expected property <$name> to be <$expected> but was <$actual>"
    )

}