package strucut

internal data class StructObject(
    private val name: String,
    private val isOptional: Boolean,
    private val value: StructRoot
) : Struct() {

    override fun verify(topology: Topology) {
        if (assertExists(topology))
            assertValueEquals(topology)
    }

    private fun assertExists(topology: Topology): Boolean {
        if (!topology.containsKey(name)) {
            if (isOptional) return false
            throw StrucutAssertionError.ObjectInObjectMissing(name, topology.keys)
        }
        return true
    }

    private fun assertValueEquals(topology: Topology) {
        val actualValue = topology[name]
        if (actualValue is Map<*, *>) {
            return value.verify(actualValue as Topology)
        }
        if (actualValue is Iterable<*>) {
            return actualValue.forEach {
                value.verify(it as Topology)
            }
        }
        throw StrucutAssertionError.ObjectAsProperty(name, actualValue)
    }

}