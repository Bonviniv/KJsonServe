package visitor

import model.*

class ArrayHomogeneityVisitor : JsonVisitor {
    private var currentType: Class<*>? = null
    private var isHomogeneous = true

    override fun visitObject(jsonObject: JsonObject) {
        // Objects don't affect homogeneity check
    }

    override fun visitArray(jsonArray: JsonArray) {
        jsonArray.elements().forEach { it.accept(this) }
    }

    override fun visitString(jsonString: JsonString) {
        checkType(JsonString::class.java)
    }

    override fun visitNumber(jsonNumber: JsonNumber) {
        checkType(JsonNumber::class.java)
    }

    override fun visitBoolean(jsonBoolean: JsonBoolean) {
        checkType(JsonBoolean::class.java)
    }

    override fun visitNull(jsonNull: JsonNull) {
        checkType(JsonNull::class.java)
    }

    private fun checkType(type: Class<*>) {
        if (currentType == null) {
            currentType = type
        } else if (currentType != type) {
            isHomogeneous = false
        }
    }

    fun isHomogeneous(): Boolean = isHomogeneous
}