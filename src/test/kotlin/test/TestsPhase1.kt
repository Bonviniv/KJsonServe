package test

import model.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes da Fase 1 do projeto.
 * Verifica a serialização de tipos JSON básicos: string, número, booleano e nulo.
 */
class TestsPhase1 {

    /**
     * Garante que os valores básicos são convertidos corretamente
     * para as suas representações padrão em JSON.
     */
    @Test
    fun testBasicValues() {
        val str = JsonString("test")
        assertEquals("\"test\"", str.serialize())

        val num = JsonNumber(42)
        assertEquals("42", num.serialize())

        val bool = JsonBoolean(true)
        assertEquals("true", bool.serialize())

        assertEquals("null", JsonNull.serialize())
    }
}