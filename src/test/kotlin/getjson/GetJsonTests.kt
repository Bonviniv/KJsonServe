package getjson

import com.sun.net.httpserver.HttpServer
import getjson.annotations.Mapping
import getjson.annotations.Param
import getjson.annotations.Path
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Testes de integração para a framework `GetJson`.
 * Verifica o correto mapeamento de rotas, parâmetros e a serialização JSON.
 */
@RunWith(JUnit4::class)
class GetJsonTests {

    /**
     * Controlador de teste com vários endpoints GET
     * que ilustram as funcionalidades suportadas pela framework.
     */
    @Mapping("api")
    class MyController {

        @Mapping("ints")
        fun demo(): List<Int> = listOf(1, 2, 3)

        @Mapping("pair")
        fun pair(): Pair<String, String> = "um" to "dois"

        @Mapping("path/{id}")
        fun path(@Path id: String): String = "$id!"

        @Mapping("args")
        fun args(
            @Param("n") n: Int,
            @Param("text") text: String
        ): Map<String, String> = mapOf(text to text.repeat(n))
    }

    private lateinit var server: HttpServer
    private val client = HttpClient.newHttpClient()

    /**
     * Inicia o servidor antes de cada teste.
     */
    @Before
    fun setUp() {
        server = GetJson(MyController::class).start(0)
    }

    /**
     * Termina o servidor após cada teste.
     */
    @After
    fun tearDown() {
        server.stop(0)
    }

    /**
     * Envia um pedido HTTP GET ao servidor e devolve a resposta como string.
     */
    private fun get(path: String): String {
        val port = server.address.port
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:$port$path"))
            .GET()
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        assertEquals(200, response.statusCode())
        return response.body()
    }

    /**
     * Testa um endpoint que devolve uma lista de inteiros.
     */
    @Test
    fun testInts() {
        assertEquals("[1,2,3]", get("/api/ints"))
    }

    /**
     * Testa um endpoint que devolve um par de strings serializado como objeto JSON.
     */
    @Test
    fun testPair() {
        assertEquals("{\"first\":\"um\",\"second\":\"dois\"}", get("/api/pair"))
    }

    /**
     * Testa a substituição de variáveis no caminho da rota (`@Path`).
     */
    @Test
    fun testPathVar() {
        assertEquals("\"foo!\"", get("/api/path/foo"))
        assertEquals("\"bar!\"", get("/api/path/bar"))
    }

    /**
     * Testa a extração de parâmetros da query string (`@Param`).
     */
    @Test
    fun testArgs() {
        assertEquals("{\"PA\":\"PAPAPA\"}", get("/api/args?n=3&text=PA"))
    }
}