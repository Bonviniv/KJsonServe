package test

import model.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes iniciais da Fase 2.
 * Valida a construção e serialização de uma estrutura JSON composta.
 */
class TestsPhase2 {

    /**
     * Testa a criação de um `JsonObject` com um campo
     * e verifica se a serialização é produzida corretamente.
     */
    @Test
    fun testComplexStructures() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        assertEquals("{\"test\":\"value\"}", obj.serialize())
    }
}