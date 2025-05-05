package test

import model.*
import visitor.JsonVisitor
import org.junit.Assert.*
import org.junit.Test

class JsonObjectTest {

    @Test
    fun testCreateEmptyObject() {
        val obj = JsonObject()
        assertEquals(0, obj.size())
        assertEquals("{}", obj.serialize())
    }

    @Test
    fun testPutAndGetValue() {
        val obj = JsonObject()
        obj.set("chave", JsonString("valor"))

        assertEquals(JsonString("valor"), obj.get("chave"))
        assertEquals(1, obj.size())
    }

    @Test
    fun testSize() {
        val obj = JsonObject()
        assertEquals(0, obj.size())

        obj.set("chave1", JsonString("valor1"))
        assertEquals(1, obj.size())

        obj.set("chave2", JsonNumber(42.0))
        assertEquals(2, obj.size())
    }

    @Test
    fun testSerialize() {
        val obj = JsonObject()
        assertEquals("{}", obj.serialize())

        obj.set("nome", JsonString("Teste"))
        assertEquals("{\"nome\":\"Teste\"}", obj.serialize())

        obj.set("idade", JsonNumber(30.0))
        // A ordem pode variar, então verificamos se contém as substrings esperadas
        val serialized = obj.serialize()
        assertTrue(serialized.startsWith("{"))
        assertTrue(serialized.endsWith("}"))
        assertTrue(serialized.contains("\"nome\":\"Teste\""))
        assertTrue(serialized.contains("\"idade\":30"))
        assertTrue(serialized.contains(","))
    }

    @Test
    fun testAccept() {
        val obj = JsonObject()
        obj.set("chave", JsonString("valor"))

        val mockVisitor = object : JsonVisitor {
            var visitedObject = false

            override fun visitObject(obj: JsonObject) {
                visitedObject = true
            }

            override fun visitArray(array: JsonArray) {}
            override fun visitString(str: JsonString) {}
            override fun visitNumber(num: JsonNumber) {}
            override fun visitBoolean(bool: JsonBoolean) {}
            override fun visitNull(jsonNull: JsonNull) {}
        }

        obj.accept(mockVisitor)
        assertTrue(mockVisitor.visitedObject)
    }

    @Test
    fun testJsonObjectFilter() {
        val obj = JsonObject().apply {
            set("keep", JsonString("yes"))
            set("remove", JsonNumber(0.0))
        }
        val filtered = obj.filter { key, _ -> key == "keep" }
        assertEquals(1, filtered.size())
        assertNotNull(filtered.get("keep"))
        assertNull(filtered.get("remove"))
        // Verifica se o original não foi mutado:
        assertNotNull(obj.get("remove"))
    }

}

