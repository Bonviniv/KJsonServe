package model

/**
 * Interface para o padrão Visitor que permite realizar operações sobre diferentes tipos de valores JSON
 */
interface JsonVisitor {
    /**
     * Processa um objeto JSON
     * @param jsonObject O objeto a ser processado
     */
    fun visitObject(jsonObject: JsonObject)

    /**
     * Processa um array JSON
     * @param jsonArray O array a ser processado
     */
    fun visitArray(jsonArray: JsonArray)

    /**
     * Processa um valor string JSON
     * @param jsonString O valor string a ser processado
     */
    fun visitString(jsonString: JsonString)

    /**
     * Processa um valor numérico JSON
     * @param jsonNumber O valor numérico a ser processado
     */
    fun visitNumber(jsonNumber: JsonNumber)

    /**
     * Processa um valor booleano JSON
     * @param jsonBoolean O valor booleano a ser processado
     */
    fun visitBoolean(jsonBoolean: JsonBoolean)

    /**
     * Processa um valor nulo JSON
     * @param jsonNull O valor nulo a ser processado
     */
    fun visitNull(jsonNull: JsonNull)
}
