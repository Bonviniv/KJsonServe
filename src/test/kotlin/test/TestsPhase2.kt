package test

import model.*
import org.junit.Test
import org.junit.Assert.*

class TestsPhase2 {
    @Test
    fun testComplexStructures() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        assertEquals("{\"test\":\"value\"}", obj.serialize())
    }
}