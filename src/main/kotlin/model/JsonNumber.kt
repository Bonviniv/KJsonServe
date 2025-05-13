package model

import visitor.JsonVisitor
import model.JsonValue

data class JsonNumber(val value: Number) : JsonValue() {
    override fun serialize(): String {
        return if (value.toDouble() % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }
    
    override fun accept(visitor: JsonVisitor) {
        visitor.visitNumber(this)
    }
}