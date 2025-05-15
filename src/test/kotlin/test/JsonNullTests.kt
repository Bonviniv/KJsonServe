package test

import model.JsonNull
import org.junit.Test
import org.junit.Assert.*

/**
 * Teste unitário para a classe `JsonNull`.
 * Verifica se a serialização devolve corretamente a string `"null"`.
 */
class JsonNullTests {

    /**
     * Testa a serialização de um valor nulo em JSON.
     */
    @Test
    fun testNullSerialization() {
        assertEquals("null", JsonNull.serialize())
    }
}