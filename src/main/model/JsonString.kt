
package model

// Representa uma string JSON
class JsonString(
    val value: String
) : JsonValue() {

    override fun serialize(): String {
        // Escapa aspas e barras (JSON encoding simples)
        val escaped = value.replace("\"", "\\\"")
        return "\"$escaped\""
    }
}
