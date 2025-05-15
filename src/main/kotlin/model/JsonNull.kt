package model

import visitor.JsonVisitor

/**
 * Representa um valor nulo (null) no formato JSON.
 * Utiliza o padrão Singleton.
 */
object JsonNull : JsonValue() {

    /**
     * Serializa este valor nulo para uma string no formato JSON.
     * @return a string "null".
     */
    override fun serialize(): String = "null"

    /**
     * Aceita um visitante para aplicar uma operação sobre este valor nulo JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitNull(this)
    }
}
