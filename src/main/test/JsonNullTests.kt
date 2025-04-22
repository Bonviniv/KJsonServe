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

class JsonNullTest {

    @Test
    fun testSerialize() {
        val nullValue = JsonNull()
        assertEquals("null", nullValue.serialize())
    }

    @Test
    fun testAccept() {
        val nullValue = JsonNull()

        val mockVisitor = object : JsonVisitor {
            var visitedNull = false

            override fun visitNull(jsonNull: JsonNull) {
                visitedNull = true
            }

            override fun visitArray(array: JsonArray) {}
            override fun visitObject(obj: JsonObject) {}
            override fun visitString(str: JsonString) {}
            override fun visitNumber(num: JsonNumber) {}
            override fun visitBoolean(bool: JsonBoolean) {}
        }

        nullValue.accept(mockVisitor)
        assertTrue(mockVisitor.visitedNull)
    }
}

