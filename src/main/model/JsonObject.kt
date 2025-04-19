
package model

// Representa um objeto JSON (mapa de chaves para valores JSON)
class JsonObject(
    private val properties: Map<String, JsonValue> = emptyMap()
) : JsonValue() {

    override fun serialize(): String {
        return properties.entries.joinToString(prefix = "{", postfix = "}") { (k, v) ->
            "\"${k.replace("\"", "\\\"")}\":${v.serialize()}"
        }
    }

    fun get(key: String): JsonValue? = properties[key]

    fun keys(): Set<String> = properties.keys

    fun values(): Collection<JsonValue> = properties.values

    fun entries(): Set<Map.Entry<String, JsonValue>> = properties.entries

    fun size(): Int = properties.size
}
