package model

import visitor.JsonVisitor

/**
 * Representa um valor booleano em JSON.
 * @property value O valor booleano (true ou false).
 */
data class JsonBoolean(val value: Boolean) : JsonValue() {

    /**
     * Serializa este valor booleano para o formato JSON.
     * @return uma string com o valor "true" ou "false".
     */
    override fun serialize(): String = value.toString()

    /**
     * Aceita um visitante para aplicar uma operação sobre este valor booleano JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitBoolean(this)
    }
}
