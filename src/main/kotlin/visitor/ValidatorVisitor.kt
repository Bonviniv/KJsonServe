package visitor

import model.*

class ValidatorVisitor : JsonVisitor {
    private var valid = true

    fun isValid(): Boolean = valid

    override fun visitObject(obj: JsonObject) {
        obj.keys().forEach { key ->
            if (key.isEmpty()) valid = false
            obj.get(key)?.accept(this)
        }
    }

    override fun visitArray(array: JsonArray) {
        array.elements().forEach { it.accept(this) }
    }

    override fun visitString(str: JsonString) {}
    override fun visitNumber(num: JsonNumber) {}
    override fun visitBoolean(bool: JsonBoolean) {}
    override fun visitNull(jsonNull: JsonNull) {}
}