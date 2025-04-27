package test

import model.ArrayHomogeneityVisitor
import model.JsonArray
import model.JsonBoolean
import model.JsonNull
import model.JsonNumber
import model.JsonString
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
        val array = JsonArray()
        val innerArray1 = JsonArray()
        val innerArray2 = JsonArray()
        
        // Make all arrays homogeneous
        innerArray1.add(JsonNumber(1.0))
        innerArray1.add(JsonNumber(2.0))
        
        innerArray2.add(JsonNumber(3.0))
        innerArray2.add(JsonNumber(4.0))
        
        array.add(innerArray1)
        array.add(innerArray2)
        
        val visitor = ArrayHomogeneityVisitor()
        array.accept(visitor)
        assertTrue(visitor.isHomogeneous())
    }
}
