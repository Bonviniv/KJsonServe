package test

import model.JsonNull
import org.junit.Test
import org.junit.Assert.*

class JsonNullTests {
    @Test
    fun testNullSerialization() {
        val nullValue = JsonNull.INSTANCE
        assertEquals("null", nullValue.serialize())
    }
}

