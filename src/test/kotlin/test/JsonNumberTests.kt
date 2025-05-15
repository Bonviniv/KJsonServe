package test

import model.JsonNumber
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para a classe `JsonNumber`.
 * Verifica o armazenamento correto do número e a sua serialização.
 */
class JsonNumberTests {

    /**
     * Testa tanto valores inteiros como decimais.
     * Garante que os números são serializados corretamente no formato JSON.
     */
    @Test
    fun testNumberValue() {
        val intValue = JsonNumber(42)
        assertEquals(42, intValue.value)
        assertEquals("42", intValue.serialize())

        val doubleValue = JsonNumber(3.14)
        assertEquals(3.14, doubleValue.value)
        assertEquals("3.14", doubleValue.serialize())
    }
}