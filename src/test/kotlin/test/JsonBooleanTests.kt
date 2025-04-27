package test

import model.JsonBoolean
import org.junit.Test
import org.junit.Assert.*

class JsonBooleanTests {
    @Test
    fun testBooleanSerialization() {
        val trueValue = JsonBoolean(true)
        val falseValue = JsonBoolean(false)
        
        assertEquals("true", trueValue.serialize())
        assertEquals("false", falseValue.serialize())
    }
}

