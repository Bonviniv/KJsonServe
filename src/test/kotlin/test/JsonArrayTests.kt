package test

import model.*
import visitor.JsonVisitor
import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para a classe `JsonArray`.
 * Verifica operações como adição, substituição, filtragem, mapeamento,
 * serialização e interação com visitantes.
 */
class JsonArrayTest {

    /**
     * Verifica a criação de um array vazio.
     */
    @Test
    fun testCreateEmptyArray() {
        val array = JsonArray()
        assertEquals(0, array.size())
        assertEquals("[]", array.serialize())
    }

    /**
     * Testa a adição e leitura de um elemento no array.
     */
    @Test
    fun testAddAndGetElement() {
        val array = JsonArray()
        val stringValue = JsonString("teste")
        array.add(stringValue)

        assertEquals(1, array.size())
        assertEquals(stringValue, array.get(0))
    }

    /**
     * Verifica se é possível substituir um elemento existente.
     */
    @Test
    fun testSetElement() {
        val array = JsonArray()
        array.add(JsonString("original"))
        array.set(0, JsonString("modificado"))

        assertEquals(JsonString("modificado"), array.get(0))
    }

    /**
     * Testa o tamanho do array após inserções.
     */
    @Test
    fun testSize() {
        val array = JsonArray()
        assertEquals(0, array.size())

        array.add(JsonString("um"))
        assertEquals(1, array.size())

        array.add(JsonNumber(42.0))
        assertEquals(2, array.size())
    }

    /**
     * Testa a filtragem de elementos do array.
     */
    @Test
    fun testFilter() {
        val array = JsonArray()
        array.add(JsonString("texto"))
        array.add(JsonNumber(10.0))
        array.add(JsonBoolean(true))

        val filtered = array.filter { it is JsonNumber }

        assertEquals(1, filtered.size())
        assertTrue(filtered.get(0) is JsonNumber)
        assertEquals(10.0, (filtered.get(0) as JsonNumber).value)
    }

    /**
     * Testa o mapeamento de elementos do array.
     * Cada número é multiplicado por 2.
     */
    @Test
    fun testMap() {
        val array = JsonArray()
        array.add(JsonNumber(1.0))
        array.add(JsonNumber(2.0))

        val mapped = array.map {
            if (it is JsonNumber) JsonNumber((it.value as Number).toDouble() * 2) else it
        }

        assertEquals(2, mapped.size())
        assertEquals(2.0, (mapped.get(0) as JsonNumber).value)
        assertEquals(4.0, (mapped.get(1) as JsonNumber).value)
    }

    /**
     * Verifica se a serialização do array JSON está correta.
     */
    @Test
    fun testSerialize() {
        val array = JsonArray()
        assertEquals("[]", array.serialize())

        array.add(JsonString("teste"))
        assertEquals("[\"teste\"]", array.serialize())

        array.add(JsonNumber(42.0))
        assertEquals("[\"teste\",42]", array.serialize())

        array.add(JsonBoolean(true))
        assertEquals("[\"teste\",42,true]", array.serialize())

        array.add(JsonNull)
        assertEquals("[\"teste\",42,true,null]", array.serialize())
    }

    /**
     * Testa a aceitação de um visitante sobre o array.
     */
    @Test
    fun testAccept() {
        val array = JsonArray()
        array.add(JsonString("teste"))

        val mockVisitor = object : JsonVisitor {
            var visitedArray = false

            override fun visitObject(obj: JsonObject) {}
            override fun visitArray(arr: JsonArray) { visitedArray = true }
            override fun visitString(str: JsonString) {}
            override fun visitNumber(num: JsonNumber) {}
            override fun visitBoolean(bool: JsonBoolean) {}
            override fun visitNull(nullValue: JsonNull) {}
        }

        array.accept(mockVisitor)
        assertTrue("Visitor deve visitar array", mockVisitor.visitedArray)
    }
}