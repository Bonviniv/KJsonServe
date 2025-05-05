package model

import visitor.JsonVisitor


/**
 * Representa um objeto JSON como um mapa de chaves (strings) para valores JSON
 * @property properties Mapa contendo os pares chave-valor do objeto JSON
 */
class JsonObject(private val properties: MutableMap<String, JsonValue> = mutableMapOf()) : JsonValue() {
    /**
     * Obtém o valor associado a uma chave específica
     * @param key A chave do valor a ser obtido
     * @return O valor JSON associado ou null se a chave não existir
     */
    fun get(key: String): JsonValue? = properties[key]
    
    /**
     * Define um valor para uma chave específica
     * @param key A chave a ser associada ao valor
     * @param value O valor JSON a ser armazenado
     */
    fun set(key: String, value: JsonValue) {
        properties[key] = value
    }

    /**
     * Retorna o número de pares chave-valor no objeto
     * @return Quantidade de elementos no objeto JSON
     */
    fun size(): Int = properties.size
    
    /**
     * Retorna uma coleção com todos os valores do objeto JSON
     * @return Collection dos valores JSON
     */
    fun values(): Collection<JsonValue> = properties.values
    
    /**
     * Retorna um conjunto com todas as chaves do objeto JSON
     * @return Set das chaves do objeto
     */
    fun keys(): Set<String> = properties.keys

    /**
     * Returns all keys in the JSON object
     */
    fun getKeys(): Set<String> = properties.keys

    /**
     * Implements the multiplication operator (*) for JSON objects
     * Returns a new object containing only the keys that exist in both objects
     */
    operator fun times(other: JsonObject): JsonObject {
        val result = JsonObject()
        for (key in this.getKeys()) {
            if (other.get(key) != null) {
                result.set(key, this.get(key)!!)
            }
        }
        return result
    }
    
    /**
     * Filtra os pares chave-valor do objeto com base em um predicado
     * @param predicate Função que determina quais pares serão mantidos
     * @return Novo JsonObject contendo apenas os pares que satisfazem o predicado
     */
    fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject {
        val filtered = JsonObject()
        for ((key, value) in properties) {
            if (predicate(key, value)) {
                filtered.set(key, value)
            }
        }
        return filtered
    }
    
    /**
     * Serializa o objeto JSON para string
     * @return String representando o objeto no formato JSON
     */
    override fun serialize(): String {
        if (properties.isEmpty()) return "{}"
        
        return properties.entries
            .sortedBy { it.key } // Sort entries by key to maintain consistent order
            .joinToString(
                prefix = "{",
                postfix = "}",
                separator = ",",
                transform = { (key, value) -> "\"$key\":${value.serialize()}" }
            )
    }
    
    /**
     * Implementação do padrão Visitor para JsonObject
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
    }

}