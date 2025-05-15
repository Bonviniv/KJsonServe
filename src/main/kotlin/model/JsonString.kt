package model

import visitor.JsonVisitor

/**
 * Representa um valor do tipo string em JSON.
 * @property value O conteúdo textual da string.
 */
data class JsonString(val value: String) : JsonValue() {

    /**
     * Serializa esta string para o formato JSON, escapando os caracteres especiais.
     * @return uma string formatada de acordo com o padrão JSON.
     */
    override fun serialize(): String {
        val escaped = value.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\b", "\\b")
            .replace("\u000C", "\\f")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")

        return "\"$escaped\""
    }

    /**
     * Aceita um visitante para aplicar uma operação sobre esta string JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitString(this)
    }
}
