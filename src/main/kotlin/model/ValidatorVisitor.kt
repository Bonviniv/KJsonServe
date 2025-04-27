package model

class ValidatorVisitor : JsonVisitor {
    private var isValid = true
    private val visitedObjects = mutableSetOf<JsonObject>()

    override fun visitObject(jsonObject: JsonObject) {
        if (!visitedObjects.add(jsonObject)) {
            isValid = false
            return
        }

        // Check for empty keys
        if (jsonObject.keys().any { it.isEmpty() }) {
            isValid = false
            return
        }

        jsonObject.values().forEach { it.accept(this) }
    }

    override fun visitArray(jsonArray: JsonArray) {
        jsonArray.elements().forEach { it.accept(this) }
    }

    override fun visitString(jsonString: JsonString) {
        // Strings are always valid
    }

    override fun visitNumber(jsonNumber: JsonNumber) {
        // Numbers are always valid
    }

    override fun visitBoolean(jsonBoolean: JsonBoolean) {
        // Booleans are always valid
    }

    override fun visitNull(jsonNull: JsonNull) {
        // Null is always valid
    }

    fun isValid(): Boolean = isValid
}