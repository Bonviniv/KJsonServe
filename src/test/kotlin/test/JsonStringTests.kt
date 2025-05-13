package test

import model.JsonString
import org.junit.Test
import org.junit.Assert.*

class JsonStringTests {
    @Test
    fun testStringValue() {
        val str = JsonString("test")
        assertEquals("test", str.value)
        assertEquals("\"test\"", str.serialize())
    }
}

