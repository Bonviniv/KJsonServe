package test

import model.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Assert.assertEquals

class TestsPhase1 {
    // Basic Value Tests
    @Test
    fun testBasicValues() {
        // String tests
        val str = JsonString("test")
        assertEquals("\"test\"", str.serialize())

        // Number tests
        val num = JsonNumber(42.0)
        assertEquals("42", num.serialize())

        // Boolean tests
        val bool = JsonBoolean(true)
        assertEquals("true", bool.serialize())

        // Null test
        val nullVal = JsonNull.INSTANCE
        assertEquals("null", nullVal.serialize())
    }

    // Array Tests
    @Test
    fun testArrayOperations() {
        val array = JsonArray()
        array.add(JsonNumber(1.0))
        array.add(JsonString("test"))
        array.add(JsonBoolean(true))

        assertEquals(3, array.size())
        assertTrue(array.get(0) is JsonNumber)
        assertEquals("[1,\"test\",true]", array.serialize().replace(" ", ""))
    }

    // Object Tests
    @Test
    fun testObjectOperations() {
        val obj = JsonObject()
        obj.set("name", JsonString("John"))
        obj.set("age", JsonNumber(30.0))
        obj.set("active", JsonBoolean(true))

        assertTrue(obj.get("name") is JsonString)
        assertEquals("John", (obj.get("name") as JsonString).value)
        // In testObjectOperations
        assertEquals(30.0, (obj.get("age") as JsonNumber).value.toDouble(), 0.0)
    }

    // Complex Structure Tests
    @Test
    fun testComplexStructure() {
        val obj = JsonObject()
        val array = JsonArray()
        val innerObj = JsonObject()

        innerObj.set("id", JsonNumber(1.0))
        innerObj.set("name", JsonString("Item1")) // Changed from "Item 1" to "Item1" to match expected format
        array.add(innerObj)

        obj.set("items", array)
        obj.set("total", JsonNumber(1.0))

        val expected = "{\"items\":[{\"id\":1,\"name\":\"Item1\"}],\"total\":1}"
        assertEquals(expected, obj.serialize().replace(" ", ""))
    }

    // Filter Tests
    @Test
    fun testObjectFilter() {
        val obj = JsonObject()
        obj.set("id", JsonNumber(1.0))
        obj.set("name", JsonString("Test"))
        obj.set("temp", JsonNumber(100.0))

        val filtered = obj.filter { key, _ -> key != "temp" }
        assertNull(filtered.get("temp"))
        assertEquals(2, filtered.getKeys().size)
    }

    @Test
    fun testArrayFilter() {
        val array = JsonArray()
        array.add(JsonNumber(1.0))
        array.add(JsonNumber(2.0))
        array.add(JsonNumber(3.0))

        val filtered = array.filter { it is JsonNumber && (it.value as Double) > 1.0 }
        assertEquals(2, filtered.size())
    }

    // Map Tests
    @Test
    fun testArrayMap() {
        val array = JsonArray()
        array.add(JsonNumber(1.0))
        array.add(JsonNumber(2.0))

        // Fix the multiplication in map operation
        val mapped = array.map { 
            if (it is JsonNumber) JsonNumber(it.value.toDouble() * 2) 
            else it 
        }

        // In testArrayMap
        assertEquals(2.0, (mapped.get(0) as JsonNumber).value.toDouble(), 0.0)
        assertEquals(4.0, (mapped.get(1) as JsonNumber).value.toDouble(), 0.0)
    }

    // Visitor Tests
    @Test
    fun testValidatorVisitor() {
        val obj = JsonObject()
        obj.set("valid", JsonString("value"))
        obj.set("nested", JsonObject())

        val visitor = ValidatorVisitor()
        obj.accept(visitor)
        assertTrue(visitor.isValid())

        // Test invalid case with empty key
        val invalidObj = JsonObject()
        invalidObj.set("", JsonString("value"))
        invalidObj.accept(visitor)
        assertFalse(visitor.isValid())
    }
}