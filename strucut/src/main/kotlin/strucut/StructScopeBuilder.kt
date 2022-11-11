package strucut

@PublishedApi
internal class StructScopeBuilder : StructScope {

    private val structs = mutableListOf<Struct>()

    override fun prop(name: String) = apply {
        structs += StructParam(name, false, null)
    }

    override fun prop(name: String, value: Any?) = apply {
        structs += StructParam(name, false, OptValue(value))
    }

    override fun prop(name: String, struct: StructScope.Builder) = apply {
        val root = StructScopeBuilder().apply { struct.apply { create() } }.build()
        structs += StructObject(name, false, root)
    }

    override fun propOpt(name: String) = apply {
        structs += StructParam(name, true, null)
    }

    override fun propOpt(name: String, value: Any?) = apply {
        structs += StructParam(name, true, OptValue(value))
    }

    override fun propOpt(name: String, struct: StructScope.Builder) = apply {
        val root = StructScopeBuilder().apply { struct.apply { create() } }.build()
        structs += StructObject(name, true, root)
    }

    fun build() = StructRoot(structs)

}