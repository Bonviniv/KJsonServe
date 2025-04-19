package model

// Representa um array JSON (lista de valores JSON)
class JsonArray(
    private val elements: List<JsonValue> = emptyList()
) : JsonValue() {

    override fun serialize(): String {
        return elements.joinToString(prefix = "[", postfix = "]") { it.serialize() }
    }

    fun get(index: Int): JsonValue = elements[index]

    fun size(): Int = elements.size

    fun toList(): List<JsonValue> = elements.toList()
}

