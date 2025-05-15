package inference

import model.*
import kotlin.reflect.full.memberProperties

/**
 * Objeto responsável por inferir estruturas JSON a partir de objetos Kotlin,
 * utilizando reflexão. Apenas tipos suportados serão convertidos.
 */
object JsonInfer {

    /**
     * Infere um valor JSON a partir de um objeto Kotlin.
     *
     * Tipos suportados:
     * - `null` → `JsonNull`
     * - `JsonValue` → retornado tal como está
     * - `String`, `Number`, `Boolean` → valores JSON primitivos
     * - `Enum` → nome do enum como string
     * - `List<T>` → array JSON
     * - `Map<String, *>` → objeto JSON
     * - Data class → objeto JSON com propriedades convertidas
     *
     * @param value o objeto Kotlin a inferir
     * @return uma instância de `JsonValue` correspondente
     */
    fun infer(value: Any?): JsonValue {
        return when (value) {
            null -> JsonNull
            is JsonValue -> value
            is String -> JsonString(value)
            is Number -> JsonNumber(value)
            is Boolean -> JsonBoolean(value)
            is Enum<*> -> JsonString(value.name)
            is List<*> -> inferList(value)
            is Map<*, *> -> inferMap(value)
            else -> inferDataClass(value)
        }
    }

    /**
     * Converte uma lista Kotlin para um array JSON.
     *
     * @param list a lista a converter
     * @return um `JsonArray` com os elementos convertidos
     */
    private fun inferList(list: List<*>): JsonArray {
        val array = JsonArray()
        list.forEach { element ->
            array.add(infer(element))
        }
        return array
    }

    /**
     * Converte um mapa Kotlin (com chaves String) num objeto JSON.
     *
     * @param map o mapa a converter
     * @return um `JsonObject` com os pares chave-valor convertidos
     */
    private fun inferMap(map: Map<*, *>): JsonObject {
        val obj = JsonObject()
        map.forEach { (key, value) ->
            if (key is String) {
                obj.set(key, infer(value))
            }
        }
        return obj
    }

    /**
     * Converte uma data class Kotlin num objeto JSON,
     * inferindo cada propriedade através de reflexão.
     *
     * @param obj a instância da data class
     * @return um `JsonObject` com as propriedades convertidas
     */
    private fun inferDataClass(obj: Any): JsonObject {
        val jsonObj = JsonObject()
        obj::class.memberProperties.forEach { prop ->
            jsonObj.set(prop.name, infer(prop.getter.call(obj)))
        }
        return jsonObj
    }
}