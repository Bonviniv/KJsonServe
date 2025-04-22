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


class JsonStringTest {

    @Test
    fun testGetValue() {
        val str = JsonString("teste")
        assertEquals("teste", str.value)
    }

    @Test
    fun testSerialize() {
        val str1 = JsonString("teste")
        assertEquals("\"teste\"", str1.serialize())

        // Teste com caracteres especiais que devem ser escapados
        val str2 = JsonString("linha1\nlinha2")
        assertEquals("\"linha1\\nlinha2\"", str2.serialize())

        val str3 = JsonString("aspas\"duplas")
        assertEquals("\"aspas\\\"duplas\"", str3.serialize())

        val str4 = JsonString("barra\\invertida")
        assertEquals("\"barra\\\\invertida\"", str4.serialize())
    }

    @Test
    fun testAccept() {
        val str = JsonString("teste")

        val mockVisitor = object : JsonVisitor {
            var visitedString = false
            var visitedValue: String? = null

            override fun visitString(str: JsonString) {
                visitedString = true
                visitedValue = str.value
            }

            override fun visitArray(array: JsonArray) {}
            override fun visitObject(obj: JsonObject) {}
            override fun visitNumber(num: JsonNumber) {}
            override fun visitBoolean(bool: JsonBoolean) {}
            override fun visitNull(jsonNull: JsonNull) {}
        }

        str.accept(mockVisitor)
        assertTrue(mockVisitor.visitedString)
        assertEquals("teste", mockVisitor.visitedValue)
    }
}

