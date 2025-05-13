package test

import model.*
import visitor.ValidatorVisitor
import org.junit.Test
import org.junit.Assert.*

class ValidatorVisitorTests {
    @Test
    fun testValidObject() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        val visitor = ValidatorVisitor()
        obj.accept(visitor)
        assertTrue(visitor.isValid())
    }
}
