package inference

import model.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

object JsonInfer {
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

    private fun inferList(list: List<*>): JsonArray {
        val array = JsonArray()
        list.forEach { element ->
            array.add(infer(element))
        }
        return array
    }

    private fun inferMap(map: Map<*, *>): JsonObject {
        val obj = JsonObject()
        map.forEach { (key, value) ->
            if (key is String) {
                obj.set(key, infer(value))
            }
        }
        return obj
    }

    private fun inferDataClass(obj: Any): JsonObject {
        val jsonObj = JsonObject()
        obj::class.memberProperties.forEach { prop ->
            jsonObj.set(prop.name, infer(prop.getter.call(obj)))
        }
        return jsonObj
    }
}
