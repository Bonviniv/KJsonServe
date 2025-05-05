package test

import inference.JsonInfer
import model.*
import org.junit.Test
import org.junit.Assert.*

class TestsPhase2 {
    // Basic Type Tests
    @Test
    fun testInferBasicTypes() {
        // Int
        val intVal = JsonInfer.infer(42)
        assertTrue(intVal is JsonNumber)
        assertEquals(42.0, (intVal as JsonNumber).value.toDouble(), 0.0)

        // Double
        val doubleVal = JsonInfer.infer(42.5)
        assertTrue(doubleVal is JsonNumber)
        assertEquals(42.5, (doubleVal as JsonNumber).value.toDouble(), 0.0)

        // Boolean
        val boolVal = JsonInfer.infer(true)
        assertTrue(boolVal is JsonBoolean)
        assertTrue((boolVal as JsonBoolean).value)

        // String
        val strVal = JsonInfer.infer("test")
        assertTrue(strVal is JsonString)
        assertEquals("test", (strVal as JsonString).value)

        // Null
        val nullVal = JsonInfer.infer(null)
        assertTrue(nullVal is JsonNull)
    }

    // Enum Test
    enum class TestEnum { VALUE1, VALUE2 }
    
    @Test
    fun testInferEnum() {
        val enumVal = JsonInfer.infer(TestEnum.VALUE1)
        assertTrue(enumVal is JsonString)
        assertEquals("VALUE1", (enumVal as JsonString).value)
    }

    // List Test
    @Test
    fun testInferList() {
        val list = listOf(1, 2, 3)
        val jsonArray = JsonInfer.infer(list)
        assertTrue(jsonArray is JsonArray)
        assertEquals(3, (jsonArray as JsonArray).size())
    }

    // Map Test
    @Test
    fun testInferMap() {
        val map = mapOf("key1" to "value1", "key2" to 2)
        val jsonObj = JsonInfer.infer(map)
        assertTrue(jsonObj is JsonObject)
        assertTrue((jsonObj as JsonObject).get("key1") is JsonString)
        assertTrue(jsonObj.get("key2") is JsonNumber)
    }

    // Data Class Tests
    data class TestPerson(
        val name: String,
        val age: Int,
        val active: Boolean
    )

    @Test
    fun testInferSimpleDataClass() {
        val person = TestPerson("John", 30, true)
        val jsonObj = JsonInfer.infer(person)
        assertTrue(jsonObj is JsonObject)
        
        val obj = jsonObj as JsonObject
        assertEquals("John", (obj.get("name") as JsonString).value)
        assertEquals(30.0, (obj.get("age") as JsonNumber).value.toDouble(), 0.0)
        assertTrue((obj.get("active") as JsonBoolean).value)
    }

    // Complex Data Class Test
    data class TestCourse(
        val name: String,
        val students: List<TestPerson>,
        val metadata: Map<String, String>
    )

    @Test
    fun testInferComplexDataClass() {
        val course = TestCourse(
            "Programming",
            listOf(TestPerson("John", 20, true)),
            mapOf("term" to "Spring")
        )

        val jsonObj = JsonInfer.infer(course) as JsonObject
        assertEquals("Programming", (jsonObj.get("name") as JsonString).value)
        assertTrue(jsonObj.get("students") is JsonArray)
        assertTrue(jsonObj.get("metadata") is JsonObject)
    }

    // Nested Null Test
    data class TestNullable(
        val value: String?,
        val nested: TestPerson?
    )

    @Test
    fun testInferWithNulls() {
        val nullable = TestNullable(null, null)
        val jsonObj = JsonInfer.infer(nullable) as JsonObject
        assertTrue(jsonObj.get("value") is JsonNull)
        assertTrue(jsonObj.get("nested") is JsonNull)
    }
}