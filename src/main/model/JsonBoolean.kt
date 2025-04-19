
package model

// Representa um valor booleano JSON (true ou false)
class JsonBoolean(
    val value: Boolean
) : JsonValue() {

    override fun serialize(): String {
        return value.toString()
    }
}
