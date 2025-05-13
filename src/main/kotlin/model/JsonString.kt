package model

import visitor.JsonVisitor
import model.JsonValue

/**
 * Representa um valor string no formato JSON
 * @property value O valor da string
 */
data class JsonString(val value: String) : JsonValue() {
    override fun serialize(): String {
        val escaped = value.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\b", "\\b")
            .replace("\u000C", "\\f")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
        
        return "\"$escaped\""
    }
    
    override fun accept(visitor: JsonVisitor) {
        visitor.visitString(this)
    }
}