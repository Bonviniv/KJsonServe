package test

import model.*
import visitor.ArrayHomogeneityVisitor
import org.junit.Test
import org.junit.Assert.*

class ArrayHomogeneityVisitorTests {
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
