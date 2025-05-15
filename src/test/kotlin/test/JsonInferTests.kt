package test

import model.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Teste que valida o comportamento de acesso e tipo em `JsonObject`.
 * (Nota: apesar do nome, não testa o JsonInfer diretamente).
 */
class JsonInferTests {

    /**
     * Verifica se um valor inserido como `JsonString`
     * é corretamente armazenado e recuperado com o tipo esperado.
     */
    @Test
    fun testBasicInference() {
        val obj = JsonObject()
        obj.set("test", JsonString("value"))
        assertTrue(obj.get("test") is JsonString)
    }
}
