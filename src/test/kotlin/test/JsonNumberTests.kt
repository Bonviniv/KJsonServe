package test

import model.JsonNumber
import org.junit.Test
import org.junit.Assert.*

class JsonNumberTests {
    @Test
    fun testNumberValue() {
        val intValue = JsonNumber(42)
        assertEquals(42, intValue.value)
        assertEquals("42", intValue.serialize())

        val doubleValue = JsonNumber(3.14)
        assertEquals(3.14, doubleValue.value)
        assertEquals("3.14", doubleValue.serialize())
    }
}

