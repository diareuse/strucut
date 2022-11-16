package strucut

/**
 * Scope permitting creating a structure. Structure is defined by key-value pairs,
 * where value can take several forms and shapes. To create a scope outside of the
 * main verification body, use [structureScopeOf] which returns built scope.
 *
 * It's not permitted to cross-cut scopes, every object needs a separate scope,
 * which is in-turn guarded by [StructScopeDsl]. If you're using java, avoid using
 * outside scopes at all times, as it can produce unwanted consequences.
 *
 * @see structureScopeOf
 * */
@StructScopeDsl
interface StructScope {

    /**
     * Defines a property. It will be **always** checked for presence. If this
     * property does not exist, then verifier throws a
     * [StrucutAssertionError.PropertyMissing].
     *
     * Note that properties that are numbers are implicitly converted to [Double].
     *
     * @param name name of the serialized field
     * @return this scope for convenience
     * */
    fun prop(name: String): StructScope

    /**
     * Defines a property. It will be **always** checked for presence AND requires
     * [value] to match.
     *
     * @param name name of the serialized field
     * @param value can be anything but a [Map]. Prefer [List] over [Array]
     * whenever you're checking iterable value.
     * @return this scope for convenience
     * */
    fun prop(name: String, value: Any?): StructScope

    /**
     * Defines an object. It will be **always** checked for presence AND requires
     * the value to be object AND checks all child properties.
     *
     * @param name name of the serialized field
     * @param struct creates a new empty structure scope
     * @return this scope for convenience
     * @see Builder
     * */
    fun prop(name: String, struct: Builder): StructScope

    /**
     * Defines a property. It will require [value] to match, only if the property
     * exists.
     *
     * @param name name of the serialized field
     * @param value can be anything but a [Map]. Prefer [List] over [Array]
     * whenever you're checking iterable value.
     * @return this scope for convenience
     * */
    fun propOpt(name: String, value: Any?): StructScope

    /**
     * Defines an object. It requires value to be an object, if it exists, AND
     * check all child properties.
     *
     * @param name name of the serialized field
     * @param struct creates a new empty structure scope
     * @return this scope for convenience
     * @see Builder
     * */
    fun propOpt(name: String, struct: Builder): StructScope

    /**
     * Builder which creates a new scope upon calling. Scope represents an object
     * or an array of objects.
     * */
    fun interface Builder {

        /**
         * Fills a scope with definitions invoked within this method body.
         *
         * @see StructScope
         * @see prop
         * @see propOpt
         * */
        fun StructScope.create()

    }

    companion object {

        /**
         * Creates a new scope, this is a convenience method for [Builder]
         *
         * @see Builder
         * */
        @JvmStatic
        fun structureScopeOf(builder: Builder): Builder = builder

    }

}