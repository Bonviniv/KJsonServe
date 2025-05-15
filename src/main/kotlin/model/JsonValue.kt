package model

import visitor.JsonVisitor

/**
 * Representa um valor JSON abstrato.
 * Esta classe é a base para todos os tipos concretos de valores JSON,
 * como objetos, arrays, strings, números, booleanos e nulo.
 */
abstract class JsonValue {

    /**
     * Converte este valor JSON para a sua representação em string,
     * segundo o formato padrão JSON.
     * @return uma string formatada em JSON.
     */
    abstract fun serialize(): String

    /**
     * Aceita um visitante para aplicar uma operação sobre este valor JSON.
     * Implementa o padrão Visitor.
     * @param visitor o visitante a aplicar.
     */
    abstract fun accept(visitor: JsonVisitor)
}
