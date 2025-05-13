
package getjson

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import getjson.annotations.Mapping
import getjson.annotations.Param
import getjson.annotations.Path
import inference.JsonInfer
import model.JsonValue
import java.net.InetSocketAddress
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

class GetJson(vararg controllerClasses: KClass<*>) {

    private data class Route(
        val rawPath: String,
        val instance: Any,
        val method: KFunction<*>
    )

    private val routes = mutableListOf<Route>()

    init {
        for (ctrlClass in controllerClasses) {
            val classMapping = ctrlClass.findAnnotation<Mapping>()?.value ?: continue
            val instance = ctrlClass.createInstance()
            for (fn in ctrlClass.declaredMemberFunctions) {
                val fnMapping = fn.findAnnotation<Mapping>() ?: continue
                val fullPath = "/" + listOf(classMapping, fnMapping.value)
                    .joinToString("/") { it.trim('/') }
                routes += Route(fullPath, instance, fn)
            }
        }
    }

    fun start(port: Int): HttpServer {
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext("/") { exchange ->
            handle(exchange)
        }
        server.start()
        println("🚀 GetJson listening on port ${server.address.port}")
        return server
    }

    private fun handle(exchange: HttpExchange) {
        val requestPath = exchange.requestURI.path
        val route = routes.firstOrNull { match(it.rawPath, requestPath) }

        if (route == null) {
            exchange.sendResponseHeaders(404, -1)
            exchange.close()
            return
        }

        try {
            val args = buildArgs(exchange, route)
            val result = route.method.call(route.instance, *args.toTypedArray())
            val json: JsonValue = JsonInfer.infer(result)
            val body = json.serialize().toByteArray(Charsets.UTF_8)

            exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
            exchange.sendResponseHeaders(200, body.size.toLong())
            exchange.responseBody.use { it.write(body) }
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, -1)
        } finally {
            exchange.close()
        }
    }

    private fun match(routePattern: String, path: String): Boolean {
        val routeParts = routePattern.trim('/').split('/')
        val pathParts = path.trim('/').split('/')
        if (routeParts.size != pathParts.size) return false

        return routeParts.zip(pathParts).all { (rp, pp) ->
            rp.startsWith("{") && rp.endsWith("}") || rp == pp
        }
    }

    private fun buildArgs(exchange: HttpExchange, route: Route): List<Any?> {
        val uri = exchange.requestURI
        val actualParts = uri.path.trim('/').split('/')
        val expectedParts = route.rawPath.trim('/').split('/')

        val pathVars: Map<String, String?> = expectedParts.mapIndexedNotNull { idx, part ->
            if (part.startsWith("{") && part.endsWith("}"))
                part.substring(1, part.length - 1) to actualParts.getOrNull(idx)
            else null
        }.toMap()

        val queryParams = uri.query
            ?.split('&')
            ?.mapNotNull {
                it.split('=').takeIf { it.size == 2 }?.let { (k, v) -> k to v }
            }?.toMap()
            ?: emptyMap()

        val args = mutableListOf<Any?>()
        for ((index, param) in route.method.parameters.withIndex()) {
            if (index == 0) continue
            val name = param.name ?: continue
            val pathAnno = param.findAnnotation<Path>()
            val paramAnno = param.findAnnotation<Param>()

            when {
                pathAnno != null -> args += pathVars[name]
                paramAnno != null -> {
                    val key = paramAnno.value.ifEmpty { name }
                    args += convert(queryParams[key], param.type.classifier as KClass<*>)
                }
                else -> args += null
            }
        }

        return args
    }

    private fun convert(raw: String?, target: KClass<*>): Any? {
        if (raw == null) return null
        return when (target) {
            String::class -> raw
            Int::class -> raw.toInt()
            Double::class -> raw.toDouble()
            Boolean::class -> raw.toBoolean()
            else -> throw IllegalArgumentException("Unsupported parameter type: $target")
        }
    }
}