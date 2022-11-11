package strucut

internal sealed class Struct {

    @Throws(StrucutAssertionError::class)
    abstract fun verify(topology: Topology)

}
