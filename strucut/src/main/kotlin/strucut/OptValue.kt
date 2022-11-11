package strucut

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@JvmInline
internal value class OptValue(val value: Any?)

@OptIn(ExperimentalContracts::class)
internal fun OptValue?.isDefined(): Boolean {
    contract {
        returns(true) implies (this@isDefined != null)
    }
    return this != null
}