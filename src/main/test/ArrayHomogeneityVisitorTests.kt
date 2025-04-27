package test

import model.JsonArray
import model.JsonBoolean
import model.JsonNull
import model.JsonNumber
import model.JsonString
import visitor.ArrayHomogeneityVisitor
import org.junit.Assert.*
import org.junit.Test

/**
 * Testes para ArrayHomogeneityVisitor: garante que cada JsonArray
 * só contém elementos não-null do mesmo tipo.
 */
class ArrayHomogeneityVisitorTests {

    @Test
    fun testEmptyArrayIsHomogeneous() {
        val arr = JsonArray()
        val visitor = ArrayHomogeneityVisitor()
        arr.accept(visitor)
        assertTrue("Array vazio deve ser homogéneo", visitor.isHomogeneous())
    }

    @Test
    fun testHomogeneousNumberArray() {
        val arr = JsonArray()
        arr.add(JsonNumber(1.0))
        arr.add(JsonNumber(2.0))
        val visitor = ArrayHomogeneityVisitor()
        arr.accept(visitor)
        assertTrue("Todos números → homogéneo", visitor.isHomogeneous())
    }

    @Test
    fun testHeterogeneousArray() {
        val arr = JsonArray()
        arr.add(JsonNumber(1.0))
        arr.add(JsonString("x"))
        val visitor = ArrayHomogeneityVisitor()
        arr.accept(visitor)
        assertFalse("Número + String → heterogéneo", visitor.isHomogeneous())
    }

    @Test
    fun testNestedArraysAndObjects() {
        // Cria [ [1,2], [], ["a"], [true] ]
        // Cada sub-array é homogéneo, e o array externo só contém JsonArray
        val outer = JsonArray().apply {
            val a1 = JsonArray().apply { add(JsonNumber(1.0)); add(JsonNumber(2.0)) }
            val a2 = JsonArray() // vazio
            val a3 = JsonArray().apply { add(JsonString("a")) }
            val a4 = JsonArray().apply { add(JsonBoolean(true)) }
            add(a1); add(a2); add(a3); add(a4)
        }

        val visitor = ArrayHomogeneityVisitor()
        outer.accept(visitor)
        // Todos os sub-arrays são homogéneos e o array externo contém apenas JsonArray
        assertTrue("Todos os arrays (inclusive o externo) são homogéneos", visitor.isHomogeneous())
    }
}
