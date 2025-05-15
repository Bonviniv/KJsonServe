package visitor

import model.*

/**
 * Interface que define o padrão Visitor para os tipos de valores JSON.
 * Permite aplicar operações sobre cada tipo concreto de `JsonValue`
 * sem alterar as suas classes.
 */
interface JsonVisitor {

    /**
     * Visita um objeto JSON.
     * @param obj o objeto a visitar.
     */
    fun visitObject(obj: JsonObject)

    /**
     * Visita um array JSON.
     * @param array o array a visitar.
     */
    fun visitArray(array: JsonArray)

    /**
     * Visita uma string JSON.
     * @param str a string a visitar.
     */
    fun visitString(str: JsonString)

    /**
     * Visita um número JSON.
     * @param num o número a visitar.
     */
    fun visitNumber(num: JsonNumber)

    /**
     * Visita um valor booleano JSON.
     * @param bool o booleano a visitar.
     */
    fun visitBoolean(bool: JsonBoolean)

    /**
     * Visita um valor nulo JSON.
     * @param jsonNull o valor nulo a visitar.
     */
    fun visitNull(jsonNull: JsonNull)
}