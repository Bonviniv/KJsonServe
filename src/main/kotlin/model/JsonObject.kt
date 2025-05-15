package model

import visitor.JsonVisitor

/**
 * Representa um objeto JSON, armazenando pares chave-valor.
 * @property properties Mapa mutável que associa chaves (strings) a valores JSON.
 */
data class JsonObject(private val properties: MutableMap<String, JsonValue> = mutableMapOf()) : JsonValue() {

    /**
     * Obtém o valor associado a uma chave.
     * @param key a chave a procurar.
     * @return o valor JSON correspondente, ou null se não existir.
     */
    fun get(key: String): JsonValue? = properties[key]

    /**
     * Define um valor para uma determinada chave.
     * @param key a chave onde armazenar.
     * @param value o valor JSON a associar.
     */
    fun set(key: String, value: JsonValue) {
        properties[key] = value
    }

    /**
     * Devolve o número de pares chave-valor no objeto.
     * @return número de entradas no objeto.
     */
    fun size(): Int = properties.size

    /**
     * Devolve todos os valores do objeto.
     * @return coleção de valores JSON.
     */
    fun values(): Collection<JsonValue> = properties.values

    /**
     * Devolve todas as chaves do objeto.
     * @return conjunto de strings correspondentes às chaves.
     */
    fun keys(): Set<String> = properties.keys

    /**
     * Alternativa de nome para obter as chaves.
     * @return conjunto de chaves.
     */
    private fun getKeys(): Set<String> = properties.keys

    /**
     * Operador de interseção entre dois objetos JSON.
     * Retém apenas as chaves comuns e os valores do objeto atual.
     * @param other o outro objeto a comparar.
     * @return novo objeto JSON com as interseções.
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
     * Filtra os pares chave-valor com base num predicado.
     * @param predicate função que avalia cada par chave-valor.
     * @return novo objeto JSON com os pares filtrados.
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
     * Serializa o objeto para o formato JSON.
     * As chaves são ordenadas alfabeticamente.
     * @return string JSON representando o objeto.
     */
    override fun serialize(): String {
        if (properties.isEmpty()) return "{}"

        return properties.entries
            .sortedBy { it.key }
            .joinToString(
                prefix = "{",
                postfix = "}",
                separator = ",",
                transform = { (key, value) -> "\"$key\":${value.serialize()}" }
            )
    }

    /**
     * Aceita um visitante para aplicar uma operação sobre este objeto JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
    }
}
