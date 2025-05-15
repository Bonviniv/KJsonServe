package test

import model.*
import visitor.ArrayHomogeneityVisitor
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para o visitante `ArrayHomogeneityVisitor`.
 * Garante que arrays com elementos do mesmo tipo são considerados homogéneos.
 */
class ArrayHomogeneityVisitorTests {

    /**
     * Testa um array com dois números.
     * O visitante deve indicar que o array é homogéneo.
     */
    @Test
    fun testHomogeneousArray() {
        val array = JsonArray()
        array.add(JsonNumber(1))
        array.add(JsonNumber(2))
        val visitor = ArrayHomogeneityVisitor()
        array.accept(visitor)
        assertTrue(visitor.isHomogeneous())
    }
}