package strucut

class Strucut(
    private val builder: StructScope.Builder
) {

    fun verify(json: String) = verifyStructureOf(json) {
        builder.apply {
            create()
        }
    }

}

inline fun verifyStructureOf(json: String, scope: StructScope.() -> Unit) {
    val struct = StructScopeBuilder().apply(scope).build()
    try {
        verifyStructureOfInternal(json, struct)
    } catch (e: StrucutAssertionError) {
        throw e.clone()
    }
}

@Throws(StrucutAssertionError::class)
@PublishedApi
internal fun verifyStructureOfInternal(json: String, struct: Struct) {
    val topologies = json.toTopology()
    for (topology in topologies)
        struct.verify(topology)
}