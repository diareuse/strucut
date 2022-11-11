package strucut

@StructScopeDsl
interface StructScope {

    fun prop(name: String): StructScope
    fun prop(name: String, value: Any?): StructScope
    fun prop(name: String, struct: Builder): StructScope

    fun propOpt(name: String): StructScope
    fun propOpt(name: String, value: Any?): StructScope
    fun propOpt(name: String, struct: Builder): StructScope

    fun interface Builder {
        fun StructScope.create()
    }

}