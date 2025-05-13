package test

import model.JsonNull
import org.junit.Test
import org.junit.Assert.*

class JsonNullTests {
    @Test
    fun testNullSerialization() {
        assertEquals("null", JsonNull.serialize())
    }
}

