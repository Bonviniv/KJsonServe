package model

import visitor.JsonVisitor
import model.JsonValue

/**
 * Representa um valor nulo no formato JSON
 */
object JsonNull : JsonValue() {
    /**
     * Serializa o valor nulo para string no formato JSON
     * @return String "null"
     */
    override fun serialize(): String = "null"
    
    /**
     * Implementação do padrão Visitor para JsonNull
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitNull(this)
    }
}