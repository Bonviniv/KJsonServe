package visitor

import model.*

/**
 * Visitante que valida a estrutura de um modelo JSON.
 * Esta implementação verifica se todos os objetos JSON:
 * - Têm chaves não vazias;
 * - Contêm chaves únicas (garantido pela estrutura `Map`);
 * - Propaga a validação recursivamente sobre todos os elementos.
 */
class ValidatorVisitor : JsonVisitor {

    private var valid = true

    /**
     * Devolve `true` se o modelo JSON for válido,
     * de acordo com os critérios de validação.
     */
    fun isValid(): Boolean = valid

    /**
     * Valida um objeto JSON.
     * Verifica se as chaves não estão vazias e visita os valores.
     */
    override fun visitObject(obj: JsonObject) {
        obj.keys().forEach { key ->
            if (key.isEmpty()) valid = false
            obj.get(key)?.accept(this)
        }
    }

    /**
     * Valida um array JSON, visitando recursivamente os seus elementos.
     */
    override fun visitArray(array: JsonArray) {
        array.elements().forEach { it.accept(this) }
    }

    /**
     * Strings são sempre consideradas válidas.
     */
    override fun visitString(str: JsonString) {}

    /**
     * Números são sempre considerados válidos.
     */
    override fun visitNumber(num: JsonNumber) {}

    /**
     * Booleanos são sempre considerados válidos.
     */
    override fun visitBoolean(bool: JsonBoolean) {}

    /**
     * Valores nulos são sempre considerados válidos.
     */
    override fun visitNull(jsonNull: JsonNull) {}
}