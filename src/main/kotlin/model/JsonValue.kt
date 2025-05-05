package model

import visitor.JsonVisitor

/**
 * Representa um valor JSON abstrato (base para todos os tipos JSON)
 */
abstract class JsonValue {
    /**
     * Converte o valor JSON para sua representação em string
     * @return String representando o valor JSON
     */
    abstract fun serialize(): String
    
    /**
     * Aceita um visitante para processar este valor JSON
     * Implementa o padrão Visitor para permitir operações sobre a estrutura JSON
     * @param visitor O visitante a ser aceito
     */
    abstract fun accept(visitor: JsonVisitor)
}
