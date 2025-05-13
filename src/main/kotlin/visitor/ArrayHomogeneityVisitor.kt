package visitor

import model.*

class ArrayHomogeneityVisitor : JsonVisitor {
    private var currentType: Class<*>? = null
    private var homogeneous = true

    fun isHomogeneous(): Boolean = homogeneous

    override fun visitArray(array: JsonArray) {
        if (array.size() > 0) {
            currentType = array.get(0).javaClass
            array.elements().forEach { element ->
                if (element.javaClass != currentType) {
                    homogeneous = false
                }
                element.accept(this)
            }
        }
    }

    override fun visitObject(obj: JsonObject) {}
    override fun visitString(str: JsonString) {}
    override fun visitNumber(num: JsonNumber) {}
    override fun visitBoolean(bool: JsonBoolean) {}
    override fun visitNull(jsonNull: JsonNull) {}
}