package strucut

internal data class StructParam(
    private val name: String,
    private val isOptional: Boolean,
    private val value: OptValue?
) : Struct() {

    override fun verify(topology: Topology) {
        assertExists(topology)
        assertValueEquals(topology)
    }

    private fun assertExists(topology: Topology) {
        if (!topology.containsKey(name)) {
            if (isOptional) return
            throw StrucutAssertionError.PropertyMissing(name, topology.keys)
        }
    }

    private fun assertValueEquals(topology: Topology) {
        if (!value.isDefined()) return

        val expected = value.value
        val actual = topology[name]
        assertEquals(expected.toDoubleOrSelf(), actual)
    }

    private fun assertEquals(expected: Any?, actual: Any?) {
        if (expected == actual) return
        throw StrucutAssertionError.ValueMismatch(name, expected, actual)
    }

    private fun Any?.toDoubleOrSelf(): Any? {
        if (this is Iterable<*>) return map { it.toDoubleOrSelf() }
        if (this is Number) return toDouble()
        return this
    }

}