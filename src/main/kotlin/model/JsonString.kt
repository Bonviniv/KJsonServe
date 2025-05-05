package model

import visitor.JsonVisitor

/**
 * Representa um valor string no formato JSON
 * @property value O valor da string
 */
class JsonString(val value: String) : JsonValue() {
    /**
     * Serializa o valor string para string no formato JSON, com escape de caracteres especiais
     * @return String representando o valor string entre aspas duplas
     */
    override fun serialize(): String {
        // Escapa caracteres especiais conforme a especificação JSON
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
     * Implementação do padrão Visitor para JsonString
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitString(this)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JsonString) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}