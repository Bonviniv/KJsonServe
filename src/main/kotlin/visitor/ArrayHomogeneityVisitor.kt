package visitor

import model.*

/**
 * Visitante que verifica se todos os elementos de arrays JSON
 * são do mesmo tipo (homogéneos), ignorando valores nulos.
 */
class ArrayHomogeneityVisitor : JsonVisitor {

    private var currentType: Class<*>? = null
    private var homogeneous = true

    /**
     * Indica se o array verificado é homogéneo.
     *
     * @return `true` se todos os elementos são do mesmo tipo (exceto null), `false` caso contrário.
     */
    fun isHomogeneous(): Boolean = homogeneous

    /**
     * Verifica a homogeneidade dos elementos de um array JSON.
     * Compara o tipo do primeiro elemento com os restantes.
     */
    override fun visitArray(array: JsonArray) {
        if (array.size() > 0) {
            currentType = array.get(0).javaClass
            array.elements().forEach { element ->
                if (element.javaClass != currentType) {
                    homogeneous = false
                }
                element.accept(this)
            }
        }
    }

    /**
     * Objetos não são verificados diretamente neste visitante.
     */
    override fun visitObject(obj: JsonObject) {}

    /**
     * Strings não afetam a homogeneidade diretamente.
     */
    override fun visitString(str: JsonString) {}

    /**
     * Números não afetam a homogeneidade diretamente.
     */
    override fun visitNumber(num: JsonNumber) {}

    /**
     * Booleanos não afetam a homogeneidade diretamente.
     */
    override fun visitBoolean(bool: JsonBoolean) {}

    /**
     * Valores nulos são ignorados para efeitos de homogeneidade.
     */
    override fun visitNull(jsonNull: JsonNull) {}
}