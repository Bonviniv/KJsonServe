package test

import model.JsonNumber
import org.junit.Test
import org.junit.Assert.*

class JsonNumberTests {
    @Test
    fun testNumberSerialization() {
        val intNumber = JsonNumber(42)
        val doubleNumber = JsonNumber(42.5)
        
        assertEquals("42", intNumber.serialize())
        assertEquals("42.5", doubleNumber.serialize())
    }
}

