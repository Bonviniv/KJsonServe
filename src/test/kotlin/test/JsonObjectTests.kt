package test

import model.*
import visitor.JsonVisitor
import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para a classe `JsonObject`.
 * Verifica criação, modificação, serialização e uso com visitantes.
 */
class JsonObjectTest {

    /**
     * Verifica a criação de um objeto vazio.
     */
    @Test
    fun testCreateEmptyObject() {
        val obj = JsonObject()
        assertEquals(0, obj.size())
        assertEquals("{}", obj.serialize())
    }

    /**
     * Testa a inserção e recuperação de um valor no objeto.
     */
    @Test
    fun testPutAndGetValue() {
        val obj = JsonObject()
        obj.set("chave", JsonString("valor"))

        assertEquals(JsonString("valor"), obj.get("chave"))
        assertEquals(1, obj.size())
    }

    /**
     * Testa o aumento do tamanho após inserções.
     */
    @Test
    fun testSize() {
        val obj = JsonObject()
        assertEquals(0, obj.size())

        obj.set("chave1", JsonString("valor1"))
        assertEquals(1, obj.size())

        obj.set("chave2", JsonNumber(42.0))
        assertEquals(2, obj.size())
    }

    /**
     * Testa a serialização de um objeto com vários pares chave-valor.
     * Garante que a saída contém os elementos esperados.
     */
    @Test
    fun testSerialize() {
        val obj = JsonObject()
        assertEquals("{}", obj.serialize())

        obj.set("nome", JsonString("Teste"))
        assertEquals("{\"nome\":\"Teste\"}", obj.serialize())

        obj.set("idade", JsonNumber(30.0))
        val serialized = obj.serialize()
        assertTrue(serialized.startsWith("{"))
        assertTrue(serialized.endsWith("}"))
        assertTrue(serialized.contains("\"nome\":\"Teste\""))
        assertTrue(serialized.contains("\"idade\":30"))
        assertTrue(serialized.contains(","))
    }

    /**
     * Testa se o método `accept` invoca corretamente o visitante.
     */
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

    /**
     * Testa a funcionalidade de `filter` num objeto JSON.
     * Garante que apenas as chaves filtradas são mantidas
     * e que o objeto original não é alterado.
     */
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
        assertNotNull(obj.get("remove")) // verifica imutabilidade
    }
}