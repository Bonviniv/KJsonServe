package test

import model.*
import org.junit.Test
import org.junit.Assert.*

class JsonInferTests {
    @Test
    fun testBasicInference() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        assertTrue(obj.get("test") is JsonString)
    }
}