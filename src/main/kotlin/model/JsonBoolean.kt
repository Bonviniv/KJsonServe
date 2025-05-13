package model

import visitor.JsonVisitor
import model.JsonValue

data class JsonBoolean(val value: Boolean) : JsonValue() {
    override fun serialize(): String = value.toString()
    
    override fun accept(visitor: JsonVisitor) {
        visitor.visitBoolean(this)
    }
}