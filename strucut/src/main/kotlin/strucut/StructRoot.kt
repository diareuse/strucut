package strucut

internal data class StructRoot(
    private val structs: Iterable<Struct>
) : Struct() {

    override fun verify(topology: Topology) {
        for (struct in structs) {
            struct.verify(topology)
        }
    }

}