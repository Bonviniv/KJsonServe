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


class JsonBooleanTest {

    @Test
    fun testGetValue() {
        val trueBool = JsonBoolean(true)
        val falseBool = JsonBoolean(false)

        assertTrue(trueBool.value)
        assertFalse(falseBool.value)
    }

    @Test
    fun testSerialize() {
        val trueBool = JsonBoolean(true)
        val falseBool = JsonBoolean(false)

        assertEquals("true", trueBool.serialize())
        assertEquals("false", falseBool.serialize())
    }

    @Test
    fun testAccept() {
        val bool = JsonBoolean(true)

        val mockVisitor = object : JsonVisitor {
            var visitedBoolean = false
            var visitedValue: Boolean? = null

            override fun visitBoolean(bool: JsonBoolean) {
                visitedBoolean = true
                visitedValue = bool.value
            }

            override fun visitArray(array: JsonArray) {}
            override fun visitObject(obj: JsonObject) {}
            override fun visitString(str: JsonString) {}
            override fun visitNumber(num: JsonNumber) {}
            override fun visitNull(jsonNull: JsonNull) {}
        }

        bool.accept(mockVisitor)
        assertTrue(mockVisitor.visitedBoolean)
        assertTrue(mockVisitor.visitedValue!!)
    }
}

