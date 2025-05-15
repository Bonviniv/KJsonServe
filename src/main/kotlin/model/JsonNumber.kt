package model

import visitor.JsonVisitor

/**
 * Representa um valor numérico em JSON.
 * @property value O número (inteiro ou decimal) a ser representado.
 */
data class JsonNumber(val value: Number) : JsonValue() {

    /**
     * Serializa este número para o formato JSON.
     * Remove casas decimais desnecessárias quando o número é inteiro.
     * @return uma string representando o número no formato JSON.
     */
    override fun serialize(): String {
        return if (value.toDouble() % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    /**
     * Aceita um visitante para aplicar uma operação sobre este número JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitNumber(this)
    }
}
