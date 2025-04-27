package test

import model.JsonObject
import model.JsonNumber
import model.JsonBoolean
import model.JsonNull
import visitor.ValidatorVisitor
import org.junit.Assert.*
import org.junit.Test

/**
 * Testes para o ValidatorVisitor, que valida a estrutura de JsonObjects.
 */
class ValidatorVisitorTests {

    @Test
    fun testValidObjectNoEmptyKeys() {
        // Objeto com chaves não vazias deve ser considerado válido
        val obj = JsonObject()
        obj.set("a", JsonNumber(1.0))
        obj.set("b", JsonBoolean(true))

        val visitor = ValidatorVisitor()
        obj.accept(visitor)

        assertTrue("Objeto sem chaves vazias deve ser válido", visitor.isValid())
    }

    @Test
    fun testObjectWithEmptyKeyIsInvalid() {
        // Objeto contendo chave vazia deve ser considerado inválido
        val obj = JsonObject()
        obj.set("", JsonNull.INSTANCE)

        val visitor = ValidatorVisitor()
        obj.accept(visitor)

        assertFalse("Objeto com chave vazia deve ser inválido", visitor.isValid())
    }

    @Test
    fun testNestedObjectsTraversal() {
        // Deve validar também objetos aninhados corretamente
        val inner = JsonObject()
        inner.set("x", JsonNumber(2.0))
        val outer = JsonObject()
        outer.set("inner", inner)

        val visitor = ValidatorVisitor()
        outer.accept(visitor)

        assertTrue("Visitor deve validar objetos aninhados corretamente", visitor.isValid())
    }
}
