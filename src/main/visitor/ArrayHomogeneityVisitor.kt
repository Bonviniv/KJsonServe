package visitor

import model.JsonVisitor
import model.JsonArray
import model.JsonObject
import model.JsonString
import model.JsonNumber
import model.JsonBoolean
import model.JsonNull

/**
 * Visitor que garante que, em cada JsonArray,
 * todos os elementos não-null sejam do mesmo tipo.
 */
class ArrayHomogeneityVisitor : JsonVisitor {
    private var homogeneous = true

    /** Retorna true se nenhum array heterogéneo foi encontrado */
    fun isHomogeneous(): Boolean = homogeneous

    /** Reinicia o estado interno, se quiseres reutilizar o mesmo visitor */
    fun reset() { homogeneous = true }

    override fun visitArray(jsonArray: JsonArray) {
        // 1) Determinar o tipo do primeiro elemento não-null
        var expectedClass: Class<out Any>? = null
        for (elem in jsonArray.elements()) {
            if (elem is JsonNull) continue
            val cls = elem::class.java
            if (expectedClass == null) {
                expectedClass = cls
            } else if (cls != expectedClass) {
                homogeneous = false
                break
            }
        }
        // 2) Ainda assim, visitar recursivamente cada elemento
        for (elem in jsonArray.elements()) {
            elem.accept(this)
        }
    }

    // Para os outros nós, apenas descem recursivamente:
    override fun visitObject(jsonObject: JsonObject) {
        for (key in jsonObject.keys()) {
            jsonObject.get(key)?.accept(this)
        }
    }

    override fun visitString(jsonString: JsonString) { /* nada */ }
    override fun visitNumber(jsonNumber: JsonNumber) { /* nada */ }
    override fun visitBoolean(jsonBoolean: JsonBoolean) { /* nada */ }
    override fun visitNull(jsonNull: JsonNull) { /* nada */ }
}
