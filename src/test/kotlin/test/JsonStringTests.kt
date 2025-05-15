package test

import model.JsonString
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para a classe `JsonString`.
 * Verifica a integridade do valor e a serialização correta.
 */
class JsonStringTests {

    /**
     * Verifica se o valor é armazenado corretamente
     * e se a serialização devolve o formato JSON esperado (com aspas).
     */
    @Test
    fun testStringValue() {
        val str = JsonString("test")
        assertEquals("test", str.value)
        assertEquals("\"test\"", str.serialize())
    }
}