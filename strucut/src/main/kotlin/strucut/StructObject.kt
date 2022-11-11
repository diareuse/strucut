package strucut

internal data class StructObject(
    private val name: String,
    private val isOptional: Boolean,
    private val value: StructRoot
) : Struct() {

    override fun verify(topology: Topology) {
        assertExists(topology)
        assertValueEquals(topology)
        value.verify(topology[name] as? Topology ?: return)
    }

    private fun assertExists(topology: Topology) {
        if (!topology.containsKey(name)) {
            if (isOptional) return
            throw StrucutAssertionError.ObjectInObjectMissing(name, topology.keys)
        }
    }

    private fun assertValueEquals(topology: Topology) {
        val actualValue = topology[name]
        if (actualValue !is Map<*, *>) {
            throw StrucutAssertionError.ObjectAsProperty(name, actualValue)
        }
    }

}