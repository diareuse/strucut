package strucut

@StructScopeDsl
interface StructScope {

    fun prop(name: String)
    fun prop(name: String, value: Any?)
    fun prop(name: String, struct: StructScope.() -> Unit)

    fun propOpt(name: String)
    fun propOpt(name: String, value: Any?)
    fun propOpt(name: String, struct: StructScope.() -> Unit)

}