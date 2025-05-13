package test

import model.*
import org.junit.Test
import org.junit.Assert.*

class TestsPhase1 {
    @Test
    fun testBasicValues() {
        val str = JsonString("test")
        assertEquals("\"test\"", str.serialize())

        val num = JsonNumber(42)
        assertEquals("42", num.serialize())

        val bool = JsonBoolean(true)
        assertEquals("true", bool.serialize())

        assertEquals("null", JsonNull.serialize())
    }
}