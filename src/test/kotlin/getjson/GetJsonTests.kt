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

@RunWith(JUnit4::class)
class GetJsonTests {

    @Mapping("api")
    class MyController {
        @Mapping("ints")
        fun demo(): List<Int> = listOf(1, 2, 3)

        @Mapping("pair")
        fun pair(): Pair<String, String> = "um" to "dois"

        @Mapping("path/{id}")
        fun path(@Path id: String): String = "$id!"

        @Mapping("args")
        fun args(@Param("n") n: Int, @Param("text") text: String): Map<String, String> =
            mapOf(text to text.repeat(n))
    }

    private lateinit var server: HttpServer
    private val client = HttpClient.newHttpClient()

    @Before
    fun setUp() {
        server = GetJson(MyController::class).start(0)
    }

    @After
    fun tearDown() {
        server.stop(0)
    }

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

    @Test
    fun testInts() {
        assertEquals("[1,2,3]", get("/api/ints"))
    }

    @Test
    fun testPair() {
        assertEquals("{\"first\":\"um\",\"second\":\"dois\"}", get("/api/pair"))
    }

    @Test
    fun testPathVar() {
        assertEquals("\"foo!\"", get("/api/path/foo"))
        assertEquals("\"bar!\"", get("/api/path/bar"))
    }

    @Test
    fun testArgs() {
        assertEquals("{\"PA\":\"PAPAPA\"}", get("/api/args?n=3&text=PA"))
    }
}