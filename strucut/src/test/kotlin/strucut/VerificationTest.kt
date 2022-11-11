package strucut

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strucut.StrucutAssertionError.*
import strucut.util.countOccurrence
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import kotlin.test.assertEquals

class VerificationTest {

    @Test
    fun verifies_requiredProp_withValue_Int() {
        verifyStructureOf("""{"property":2}""") {
            prop("property", 2)
        }
    }

    @Test
    fun verifies_requiredProp_withValue_Double() {
        verifyStructureOf("""{"property":2}""") {
            prop("property", 2.0)
        }
    }

    @Test
    fun verifies_requiredProp_withValue_Float() {
        verifyStructureOf("""{"property":2}""") {
            prop("property", 2f)
        }
    }

    @Test
    fun verifies_requiredProp_withValue_String() {
        verifyStructureOf("""{"property":"boo"}""") {
            prop("property", "boo")
        }
    }

    @Test
    fun verifies_requiredProp() {
        verifyStructureOf("""{"property":2}""") {
            prop("property")
        }
    }

    @Test
    fun verifies_requiredObject() {
        verifyStructureOf("""{"property":{}}""") {
            prop("property")
        }
    }

    @Test
    fun throws_onMissingProperty_inEmptyString() {
        assertThrows<PropertyMissing> {
            verifyStructureOf("") {
                prop("property")
            }
        }
    }

    @Test
    fun throws_onMissingProperty_inEmptyObject() {
        assertThrows<PropertyMissing> {
            verifyStructureOf("{}") {
                prop("property")
            }
        }
    }

    @Test
    fun throws_onNonEqualProperty_Int() {
        assertThrows<ValueMismatch> {
            verifyStructureOf("""{"property":2}""") {
                prop("property", 3)
            }
        }
    }

    @Test
    fun throws_onNonEqualProperty_Double() {
        assertThrows<ValueMismatch> {
            verifyStructureOf("""{"property":2}""") {
                prop("property", 3.0)
            }
        }
    }

    @Test
    fun throws_onNonEqualProperty_Float() {
        assertThrows<ValueMismatch> {
            verifyStructureOf("""{"property":2}""") {
                prop("property", 3f)
            }
        }
    }

    @Test
    fun throws_onNonEqualProperty_String() {
        assertThrows<ValueMismatch> {
            verifyStructureOf("""{"property":2}""") {
                prop("property", "blow")
            }
        }
    }

    @Test
    fun verifies_requiredObject_nested() {
        verifyStructureOf("""{"property":{"nested":3}}""") {
            prop("property") {
                prop("nested", 3)
            }
        }
    }

    @Test
    fun verifies_optionalProperty_ifPresent() {
        verifyStructureOf("""{"property":3}""") {
            propOpt("property")
        }
    }

    @Test
    fun verifies_optionalProperty_ifMissing() {
        verifyStructureOf("") {
            propOpt("property")
        }
    }

    @Test
    fun throws_optionalProperty_ifPresent() {
        assertThrows<ValueMismatch> {
            verifyStructureOf("""{"property":1}""") {
                propOpt("property", 3)
            }
        }
    }

    @Test
    fun throws_optionalObject_ifProperty() {
        assertThrows<ObjectAsProperty> {
            verifyStructureOf("""{"property":1}""") {
                propOpt("property") {
                    prop("nested", 3)
                }
            }
        }
    }

    @Test
    fun throws_optionalObject_ifEmpty() {
        assertThrows<PropertyMissing> {
            verifyStructureOf("""{"property":{}}""") {
                propOpt("property") {
                    prop("nested", 3)
                }
            }
        }
    }

    @Test
    fun throws_optionalNestedObject_ifEmpty() {
        assertThrows<ObjectInObjectMissing> {
            verifyStructureOf("""{"property":{}}""") {
                propOpt("property") {
                    prop("nested") {}
                }
            }
        }
    }

    @Test
    fun throws_onIssueInSecondInArray() {
        assertThrows<PropertyMissing> {
            verifyStructureOf("""[{"property":""},{}]""") {
                prop("property")
            }
        }
    }

    @Test
    fun doesNotExpose_internalStackTrace() {
        val throwable = assertThrows<StrucutAssertionError> {
            verifyStructureOf("""{"a":{"b":{"c":{"d":{"e":""}}}}}""") {
                prop("a") {
                    prop("b") {
                        prop("c") {
                            prop("d") {
                                prop("e", "f")
                            }
                        }
                    }
                }
            }
        }
        val stackTrace = ByteArrayOutputStream().use {
            PrintWriter(it).use(throwable::printStackTrace)
            it.toByteArray().decodeToString()
        }
        assertEquals(
            expected = 0,
            actual = stackTrace.countOccurrence(Struct::verify.name),
            message = stackTrace
        )
    }

    @Test
    fun doesNotExpose_callMethod() {
        val throwable = assertThrows<StrucutAssertionError> {
            verifyStructureOf("""{"a":{"b":{"c":{"d":{"e":""}}}}}""") {
                prop("a") {
                    prop("b") {
                        prop("c") {
                            prop("d") {
                                prop("e", "f")
                            }
                        }
                    }
                }
            }
        }
        val stackTrace = ByteArrayOutputStream().use {
            PrintWriter(it).use(throwable::printStackTrace)
            it.toByteArray().decodeToString()
        }
        assertEquals(
            expected = 0,
            actual = stackTrace.countOccurrence(::verifyStructureOf.name),
            message = stackTrace
        )
    }

}
