package model

import visitor.JsonVisitor
import model.JsonValue

data class JsonObject(private val properties: MutableMap<String, JsonValue> = mutableMapOf()) : JsonValue() {
    fun get(key: String): JsonValue? = properties[key]
    
    fun set(key: String, value: JsonValue) {
        properties[key] = value
    }

    fun size(): Int = properties.size
    
    fun values(): Collection<JsonValue> = properties.values
    
    fun keys(): Set<String> = properties.keys

    fun getKeys(): Set<String> = properties.keys

    operator fun times(other: JsonObject): JsonObject {
        val result = JsonObject()
        for (key in this.getKeys()) {
            if (other.get(key) != null) {
                result.set(key, this.get(key)!!)
            }
        }
        return result
    }
    
    fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject {
        val filtered = JsonObject()
        for ((key, value) in properties) {
            if (predicate(key, value)) {
                filtered.set(key, value)
            }
        }
        return filtered
    }
    
    override fun serialize(): String {
        if (properties.isEmpty()) return "{}"
        
        return properties.entries
            .sortedBy { it.key }
            .joinToString(
                prefix = "{",
                postfix = "}",
                separator = ",",
                transform = { (key, value) -> "\"$key\":${value.serialize()}" }
            )
    }
    
    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
    }
}