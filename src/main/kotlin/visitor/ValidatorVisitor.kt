package visitor

import model.*

class ValidatorVisitor : JsonVisitor {
    private var isValid = true
    private val visitedObjects = mutableSetOf<JsonObject>()

    override fun visitObject(jsonObject: JsonObject) {
        if (!visitedObjects.add(jsonObject)) {
            isValid = false
            return
        }

        if (jsonObject.keys().any { it.isEmpty() }) {
            isValid = false
            return
        }

        jsonObject.values().forEach { it.accept(this) }
    }

    override fun visitArray(jsonArray: JsonArray) {
        jsonArray.elements().forEach { it.accept(this) }
    }

    override fun visitString(jsonString: JsonString) {}

    override fun visitNumber(jsonNumber: JsonNumber) {}

    override fun visitBoolean(jsonBoolean: JsonBoolean) {}

    override fun visitNull(jsonNull: JsonNull) {}

    fun isValid(): Boolean = isValid
}