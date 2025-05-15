package test

import model.*
import visitor.ValidatorVisitor
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para o visitante `ValidatorVisitor`.
 * Verifica se estruturas JSON com chaves válidas são corretamente aceites como válidas.
 */
class ValidatorVisitorTests {

    /**
     * Testa um objeto JSON simples com uma chave não vazia.
     * A estrutura deve ser considerada válida.
     */
    @Test
    fun testValidObject() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        val visitor = ValidatorVisitor()
        obj.accept(visitor)
        assertTrue(visitor.isValid())
    }
}