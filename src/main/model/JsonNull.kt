package model

// Representa o valor nulo JSON
object JsonNull : JsonValue() {

    override fun serialize(): String {
        return "null"
    }
}

