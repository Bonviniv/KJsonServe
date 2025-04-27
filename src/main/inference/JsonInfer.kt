package inference

import model.JsonValue
import model.JsonNull
import model.JsonString
import model.JsonNumber
import model.JsonBoolean
import model.JsonArray
import model.JsonObject
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Converte objetos Kotlin suportados em JsonValue via reflexão.
 */
object JsonInfer {
    /**
     * Infere um JsonValue a partir de um objeto Kotlin.
     * Suporta: null, JsonValue, String, Number, Boolean, List<*>, Enum<*>, Map<String, *>, data classes.
     */
    fun infer(value: Any?): JsonValue = when (value) {
        null -> JsonNull.INSTANCE
        is JsonValue -> value
        is String -> JsonString(value)
        is Number -> JsonNumber(value)
        is Boolean -> JsonBoolean(value)
        is List<*> -> {
            val array = JsonArray()
            value.forEach { array.add(infer(it)) }
            array
        }
        is Enum<*> -> JsonString(value.name)
        is Map<*, *> -> {
            val obj = JsonObject()
            value.entries.forEach { (k, v) ->
                val key = k as? String ?: throw IllegalArgumentException(
                    "Map key must be a String: found ${k?.javaClass}"
                )
                obj.set(key, infer(v))
            }
            obj
        }
        else -> {
            val kClass = value::class
            if (!kClass.isData) {
                throw IllegalArgumentException("Tipo não suportado para inferência: ${value::class}")
            }
            val obj = JsonObject()
            val constructor = kClass.primaryConstructor
            if (constructor != null) {
                for (param in constructor.parameters) {
                    val name = param.name ?: continue
                    val prop = kClass.memberProperties.first { it.name == name }
                    prop.isAccessible = true
                    val propValue = prop.getter.call(value)
                    obj.set(name, infer(propValue))
                }
            } else {
                for (prop in kClass.memberProperties) {
                    prop.isAccessible = true
                    val propValue = prop.getter.call(value)
                    obj.set(prop.name, infer(propValue))
                }
            }
            obj
        }
    }
}
