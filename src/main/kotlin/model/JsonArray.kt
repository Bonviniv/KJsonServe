package model

import visitor.JsonVisitor

/**
 * Representa um array JSON como uma lista de valores JSON.
 * @property elements Lista mutável de elementos JSON que compõem o array.
 */
data class JsonArray(private val elements: MutableList<JsonValue> = mutableListOf()) : JsonValue() {

    /**
     * Adiciona um novo valor ao array.
     * @param value o valor JSON a adicionar.
     */
    fun add(value: JsonValue) {
        elements.add(value)
    }

    /**
     * Obtém o valor na posição especificada.
     * @param index o índice do elemento.
     * @return o valor JSON correspondente.
     */
    fun get(index: Int): JsonValue = elements[index]

    /**
     * Substitui o valor na posição especificada.
     * @param index o índice onde será substituído.
     * @param value o novo valor JSON.
     */
    fun set(index: Int, value: JsonValue) {
        elements[index] = value
    }

    /**
     * Devolve o número de elementos no array.
     * @return o tamanho do array.
     */
    fun size(): Int = elements.size

    /**
     * Devolve os elementos como uma lista imutável.
     * @return lista de valores JSON.
     */
    fun elements(): List<JsonValue> = elements.toList()

    /**
     * Filtra os elementos do array de acordo com um predicado.
     * @param predicate função que decide se o elemento deve ser incluído.
     * @return novo array JSON com os elementos filtrados.
     */
    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filtered = JsonArray()
        for (element in elements) {
            if (predicate(element)) {
                filtered.add(element)
            }
        }
        return filtered
    }

    /**
     * Aplica uma transformação a cada elemento do array.
     * @param transform função que transforma cada elemento.
     * @return novo array JSON com os elementos transformados.
     */
    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val mapped = JsonArray()
        for (element in elements) {
            mapped.add(transform(element))
        }
        return mapped
    }

    /**
     * Serializa o array para uma string no formato JSON.
     * @return string representando o array em JSON.
     */
    override fun serialize(): String {
        if (elements.isEmpty()) return "[]"

        return elements.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ","
        ) { it.serialize() }
    }

    /**
     * Aceita um visitante para aplicar uma operação sobre este array JSON.
     * @param visitor o visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitArray(this)
    }
}
