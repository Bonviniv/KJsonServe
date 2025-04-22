package model

/**
 * Representa um valor booleano no formato JSON (true ou false)
 * @property value O valor booleano a ser representado
 */
class JsonBoolean(val value: Boolean) : JsonValue() {
    /**
     * Serializa o valor booleano para string no formato JSON
     * @return String representando o valor booleano ("true" ou "false")
     */
    override fun serialize(): String = value.toString()
    
    /**
     * Implementação do padrão Visitor para JsonBoolean
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitBoolean(this)
    }
}