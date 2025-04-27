package test

import model.JsonNull
import model.JsonString
import model.JsonNumber
import model.JsonBoolean
import model.JsonArray
import model.JsonObject
import inference.JsonInfer
import org.junit.Assert.*
import org.junit.Test

// Dados auxiliares para inferência
enum class TestEnum { A, B }

data class SimpleData(
    val s: String,
    val i: Int,
    val b: Boolean,
    val d: Double?
)

data class NestedData(
    val list: List<String>,
    val map: Map<String, Int>
)

data class Course(
    val name: String,
    val credits: Int,
    val evaluation: List<EvalItem>
)

data class EvalItem(
    val name: String,
    val percentage: Double,
    val mandatory: Boolean,
    val type: EvalType?
)

enum class EvalType { TEST, PROJECT, EXAM }

/**
 * Testes para a função JsonInfer.infer, cobrindo tipos suportados pelo Phase 2.
 */
class JsonInferTests {

    @Test
    fun testInferNull() {
        val j = JsonInfer.infer(null)
        assertTrue(j is JsonNull)
        assertSame(JsonNull.INSTANCE, j)
    }

    @Test
    fun testInferExistingJsonValue() {
        val original = JsonString("hello")
        val j = JsonInfer.infer(original)
        assertSame("Deve retornar a própria instância JsonValue", original, j)
    }

    @Test
    fun testInferPrimitiveTypes() {
        // String
        val js = JsonInfer.infer("text")
        assertTrue(js is JsonString)
        assertEquals("text", (js as JsonString).value)

        // Int/Number
        val jn = JsonInfer.infer(42)
        assertTrue(jn is JsonNumber)
        assertEquals(42.0, (jn as JsonNumber).value.toDouble(), 0.0)

        // Boolean
        val jb = JsonInfer.infer(false)
        assertTrue(jb is JsonBoolean)
        assertFalse((jb as JsonBoolean).value)
    }

    @Test
    fun testInferEnum() {
        val je = JsonInfer.infer(TestEnum.B)
        assertTrue(je is JsonString)
        assertEquals("B", (je as JsonString).value)
    }

    @Test
    fun testInferList() {
        val list = listOf("a", "b", "c")
        val arr = JsonInfer.infer(list)
        assertTrue(arr is JsonArray)
        val ja = arr as JsonArray
        assertEquals(3, ja.size())
        assertEquals("a", (ja.get(0) as JsonString).value)
    }

    @Test
    fun testInferMap() {
        val map = mapOf("one" to 1, "two" to 2)
        val obj = JsonInfer.infer(map)
        assertTrue(obj is JsonObject)
        val jo = obj as JsonObject
        assertEquals(1.0, (jo.get("one") as JsonNumber).value.toDouble(), 0.0)
        assertEquals(2.0, (jo.get("two") as JsonNumber).value.toDouble(), 0.0)
    }

    @Test
    fun testInferDataClassSimple() {
        val data = SimpleData("s", 7, true, null)
        val obj = JsonInfer.infer(data) as JsonObject
        assertEquals("s", (obj.get("s") as JsonString).value)
        assertEquals(7.0, (obj.get("i") as JsonNumber).value.toDouble(), 0.0)
        assertTrue((obj.get("b") as JsonBoolean).value)
        assertTrue(obj.get("d") is JsonNull)
    }

    @Test
    fun testInferDataClassNested() {
        val nested = NestedData(
            list = listOf("x", "y"),
            map = mapOf("k" to 9)
        )
        val obj = JsonInfer.infer(nested) as JsonObject
        val ja = obj.get("list") as JsonArray
        assertEquals(2, ja.size())
        assertEquals("x", (ja.get(0) as JsonString).value)
        val jm = obj.get("map") as JsonObject
        assertEquals(9.0, (jm.get("k") as JsonNumber).value.toDouble(), 0.0)
    }

    @Test
    fun testInferExampleCourse() {
        val course = Course(
            name = "PA",
            credits = 6,
            evaluation = listOf(
                EvalItem("quizzes", 0.2, false, null),
                EvalItem("project", 0.8, true, EvalType.PROJECT)
            )
        )
        val json = JsonInfer.infer(course)
        val expected = "{\"name\":\"PA\",\"credits\":6,\"evaluation\":[{\"name\":\"quizzes\",\"percentage\":0.2,\"mandatory\":false,\"type\":null},{\"name\":\"project\",\"percentage\":0.8,\"mandatory\":true,\"type\":\"PROJECT\"}]}"
        assertEquals("Estrutura inferida deve corresponder ao JSON esperado", expected, json.serialize())
    }
}