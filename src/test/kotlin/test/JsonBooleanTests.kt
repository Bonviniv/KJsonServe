package test

import model.JsonBoolean
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para a classe `JsonBoolean`.
 * Verifica o armazenamento do valor booleano e a sua serialização correta.
 */
class JsonBooleanTests {

    /**
     * Testa os valores `true` e `false`.
     * Garante que a serialização devolve as strings JSON correspondentes.
     */
    @Test
    fun testBooleanValue() {
        val trueValue = JsonBoolean(true)
        assertTrue(trueValue.value)
        assertEquals("true", trueValue.serialize())

        val falseValue = JsonBoolean(false)
        assertFalse(falseValue.value)
        assertEquals("false", falseValue.serialize())
    }
}