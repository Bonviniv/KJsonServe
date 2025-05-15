package test

import model.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes de instanciamento manual dos tipos básicos do modelo JSON.
 * Garante que os valores são corretamente atribuídos aos campos internos.
 */
class JsonInstantiationTests {

    /**
     * Verifica a criação de instâncias de `JsonString`, `JsonNumber` e `JsonBoolean`
     * e se os valores são corretamente armazenados.
     */
    @Test
    fun testInstantiation() {
        val str = JsonString("test")
        assertEquals("test", str.value)

        val num = JsonNumber(42)
        assertEquals(42, num.value)

        val bool = JsonBoolean(true)
        assertTrue(bool.value)
    }
}