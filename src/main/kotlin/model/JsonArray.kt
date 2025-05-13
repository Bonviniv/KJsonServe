package model

import visitor.JsonVisitor
import model.JsonValue

/**
 * Representa um array JSON como uma lista de valores JSON
 * @property elements Lista de elementos JSON contidos no array
 */
data class JsonArray(private val elements: MutableList<JsonValue> = mutableListOf()) : JsonValue() {
    fun add(value: JsonValue) {
        elements.add(value)
    }
    
    fun get(index: Int): JsonValue = elements[index]
    
    fun set(index: Int, value: JsonValue) {
        elements[index] = value
    }
    
    fun size(): Int = elements.size

    fun elements(): List<JsonValue> = elements.toList()

    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filtered = JsonArray()
        for (element in elements) {
            if (predicate(element)) {
                filtered.add(element)
            }
        }
        return filtered
    }
    
    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val mapped = JsonArray()
        for (element in elements) {
            mapped.add(transform(element))
        }
        return mapped
    }
    
    override fun serialize(): String {
        if (elements.isEmpty()) return "[]"
        
        return elements.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ","
        ) { it.serialize() }
    }
    
    override fun accept(visitor: JsonVisitor) {
        visitor.visitArray(this)
    }
}