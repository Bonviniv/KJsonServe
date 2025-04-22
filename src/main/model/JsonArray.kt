package model

/**
 * Representa um array JSON como uma lista de valores JSON
 * @property elements Lista de elementos JSON contidos no array
 */
class JsonArray(private val elements: MutableList<JsonValue> = mutableListOf()) : JsonValue() {
    /**
     * Adiciona um valor ao final do array
     * @param value O valor JSON a ser adicionado
     */
    fun add(value: JsonValue) {
        elements.add(value)
    }
    
    /**
     * Obtém o valor na posição especificada
     * @param index O índice do elemento a ser obtido
     * @return O valor JSON na posição especificada
     * @throws IndexOutOfBoundsException se o índice estiver fora dos limites
     */
    fun get(index: Int): JsonValue = elements[index]
    
    /**
     * Define um valor na posição especificada
     * @param index O índice onde o valor será definido
     * @param value O valor JSON a ser armazenado
     * @throws IndexOutOfBoundsException se o índice estiver fora dos limites
     */
    fun set(index: Int, value: JsonValue) {
        elements[index] = value
    }
    
    /**
     * Retorna o número de elementos no array
     * @return Quantidade de elementos no array JSON
     */
    fun size(): Int = elements.size
    
    /**
     * Filtra os elementos do array com base em um predicado
     * @param predicate Função que determina quais elementos serão mantidos
     * @return Novo JsonArray contendo apenas os elementos que satisfazem o predicado
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
     * Mapeia os elementos do array aplicando uma transformação
     * @param transform Função que transforma cada elemento
     * @return Novo JsonArray com os elementos transformados
     */
    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val mapped = JsonArray()
        for (element in elements) {
            mapped.add(transform(element))
        }
        return mapped
    }
    
    /**
     * Serializa o array JSON para string
     * @return String representando o array no formato JSON
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
     * Implementação do padrão Visitor para JsonArray
     * @param visitor O visitante a processar este valor
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitArray(this)
    }
}