package test

import model.*
import org.junit.Test
import org.junit.Assert.*

class JsonInstantiationTests {
    @Test
    fun testInstantiation() {
        val str = JsonString("test")
        assertEquals("test", str.value)
        
        val num = JsonNumber(42)
        assertEquals(42, num.value)
        
        val bool = JsonBoolean(true)
        assertTrue(bool.value)
    }
}