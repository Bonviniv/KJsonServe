package visitor

import model.JsonVisitor
import model.JsonObject
import model.JsonArray
import model.JsonString
import model.JsonNumber
import model.JsonBoolean
import model.JsonNull

/**
 * Visitor que valida JsonObjects:
 * - não permite chaves duplicadas em cada objeto
 * - não permite chaves vazias
 */
class ValidatorVisitor : JsonVisitor {
    private var valid = true

    /** Retorna true se a validação passou sem erros */
    fun isValid(): Boolean = valid

    /** Para usar o mesmo visitor várias vezes */
    fun reset() { valid = true }

    override fun visitObject(jsonObject: JsonObject) {
        val seen = mutableSetOf<String>()
        for (key in jsonObject.keys()) {
            if (key.isEmpty() || !seen.add(key)) {
                valid = false
            }
            jsonObject.get(key)?.accept(this)
        }
    }

    override fun visitArray(jsonArray: JsonArray) {
        for (elem in jsonArray.elements()) {
            elem.accept(this)
        }
    }

    override fun visitString(jsonString: JsonString) { /* nada a fazer */ }
    override fun visitNumber(jsonNumber: JsonNumber) { /* nada a fazer */ }
    override fun visitBoolean(jsonBoolean: JsonBoolean) { /* nada a fazer */ }
    override fun visitNull(jsonNull: JsonNull) { /* nada a fazer */ }
}
