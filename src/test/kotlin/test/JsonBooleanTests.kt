package test

import model.JsonBoolean
import org.junit.Test
import org.junit.Assert.*

class JsonBooleanTests {
    @Test
    fun testBooleanValue() {
        val trueValue = JsonBoolean(true)
        assertTrue(trueValue.value)
        assertEquals("true", trueValue.serialize())

        val falseValue = JsonBoolean(false)
        assertFalse(falseValue.value)
        assertEquals("false", falseValue.serialize())
    }
}

