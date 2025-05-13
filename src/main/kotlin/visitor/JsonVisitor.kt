package visitor

import model.*

interface JsonVisitor {
    fun visitObject(obj: JsonObject)
    fun visitArray(array: JsonArray)
    fun visitString(str: JsonString)
    fun visitNumber(num: JsonNumber)
    fun visitBoolean(bool: JsonBoolean)
    fun visitNull(jsonNull: JsonNull)
}
