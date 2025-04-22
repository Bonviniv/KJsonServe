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

    //@Test
    //fun testContainsKey() {
     //   val obj = JsonObject()
      //  assertFalse(obj.containsKey("chave"))

      //  obj.set("chave", JsonString("valor"))
      //  assertTrue(obj.containsKey("chave"))
   // }

    @Test
    fun testSize() {
        val obj = JsonObject()
        assertEquals(0, obj.size())

        obj.set("chave1", JsonString("valor1"))
        assertEquals(1, obj.size())

        obj.set("chave2", JsonNumber(42.0))
        assertEquals(2, obj.size())
    }

    //@Test
    //fun testRemove() {
      // val obj = JsonObject()
        //obj.set("chave", JsonString("valor"))

        //assertTrue(obj.containsKey("chave"))
        //obj.remove("chave")
        //assertFalse(obj.containsKey("chave"))
    //}

    //@Test
    //fun testFilter() {
       // val obj = JsonObject()
       // obj.set("nome", JsonString("Teste"))
       // obj.set("idade", JsonNumber(30.0))
       // obj.set("ativo", JsonBoolean(true))

       // val filtered = obj.filter { key, value -> value is JsonNumber }

        //assertEquals(1, filtered.size())
        //assertTrue(filtered.containsKey("idade"))
        //assertFalse(filtered.containsKey("nome"))
        //assertFalse(filtered.containsKey("ativo"))
    //}

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
}

