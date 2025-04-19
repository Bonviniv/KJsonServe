package model

// Representa um número JSON (inteiro ou decimal)
class JsonNumber(
    val value: Number
) : JsonValue() {

    override fun serialize(): String {
        return value.toString()
    }
}

