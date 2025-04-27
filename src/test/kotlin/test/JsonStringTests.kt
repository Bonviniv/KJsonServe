package test

import model.JsonString
import org.junit.Test
import org.junit.Assert.*

class JsonStringTests {
    @Test
    fun testStringSerialization() {
        val simpleString = JsonString("hello")
        assertEquals("\"hello\"", simpleString.serialize())
    }
}

