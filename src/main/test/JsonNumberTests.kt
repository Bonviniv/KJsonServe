package test

import model.JsonArray
import model.JsonBoolean
import model.JsonNull
import model.JsonNumber
import model.JsonObject
import model.JsonString
import model.JsonVisitor
import org.junit.Assert.*
import org.junit.Test

class JsonNumberTest {

    @Test
    fun testGetValue() {
        val num = JsonNumber(42.5)
        assertEquals(42.5, num.value)
    }

    @Test
    fun testSerializeInteger() {
        val num = JsonNumber(42.0)
        assertEquals("42", num.serialize())
    }

    @Test
    fun testSerializeDecimal() {
        val num = JsonNumber(42.5)
        assertEquals("42.5", num.serialize())
    }

    @Test
    fun testSerializeNegative() {
        val num = JsonNumber(-10.0)
        assertEquals("-10", num.serialize())
    }

    @Test
    fun testAccept() {
        val num = JsonNumber(42.0)

        val mockVisitor = object : JsonVisitor {
            var visitedNumber = false
            var visitedValue: Double? = null

            override fun visitNumber(num: JsonNumber) {
                visitedNumber = true
                visitedValue = num.value as Double?
            }

            override fun visitArray(array: JsonArray) {}
            override fun visitObject(obj: JsonObject) {}
            override fun visitString(str: JsonString) {}
            override fun visitBoolean(bool: JsonBoolean) {}
            override fun visitNull(jsonNull: JsonNull) {}
        }

        num.accept(mockVisitor)
        assertTrue(mockVisitor.visitedNumber)
        assertEquals(42.0, mockVisitor.visitedValue)
    }
}

