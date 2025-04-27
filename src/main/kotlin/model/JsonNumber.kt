package model

/**
 * Representa um valor numérico no formato JSON
 * @property value O valor numérico (Int, Long, Float, Double, etc.)
 */
class JsonNumber(val value: Number) : JsonValue() {
    /**
     * Serializa o valor numérico para string no formato JSON
     * @return String representando o valor numérico
     */
    override fun serialize(): String {
        return if (value.toDouble() % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }
    
    /**
     * Implementação do padrão Visitor para JsonNumber
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitNumber(this)
    }
}