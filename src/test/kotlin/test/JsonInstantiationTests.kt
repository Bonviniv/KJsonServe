import org.junit.Test
import org.junit.Assert.*
import model.*

class JsonInstantiationTests {

    @Test
    fun testJsonNumberInstantiation() {
        // Teste com números inteiros
        val id = JsonNumber(42.0)
        assertEquals("O valor deveria ser 42.0", 42.0, id.value as Double, 0.0)

        // Teste com números negativos
        val temperatura = JsonNumber(-5.0)
        assertEquals("O valor deveria ser -5.0", -5.0, temperatura.value as Double, 0.0)

        // Teste com números decimais
        val valor = JsonNumber(10.5)
        assertEquals("O valor deveria ser 10.5", 10.5, valor.value as Double, 0.0)
    }

    @Test
    fun testJsonBooleanInstantiation() {
        val ativo = JsonBoolean(true)
        assertTrue(ativo.value)

        val disponivel = JsonBoolean(false)
        assertFalse(disponivel.value)
    }

    @Test
    fun testJsonStringInstantiation() {
        val nome = JsonString("João Silva")
        assertEquals("João Silva", nome.value)

        val email = JsonString("joao.silva@example.com")
        assertEquals("joao.silva@example.com", email.value)

        // Teste com caracteres especiais
        val endereco = JsonString("Av. Paulista, 123")
        assertEquals("Av. Paulista, 123", endereco.value)
    }

    @Test
    fun testJsonNullInstantiation() {
        val nullValue = JsonNull
        assertNotNull(nullValue)
        assertEquals("null", nullValue.serialize())
    }

    @Test
    fun testJsonArrayInstantiation() {
        val array = JsonArray()

        // Teste array de números
        array.add(JsonNumber(1.0))
        array.add(JsonNumber(2.0))
        array.add(JsonNumber(3.0))

        assertEquals(3, array.size())
        assertTrue(array.get(0) is JsonNumber)
        assertEquals("O primeiro elemento deveria ser 1.0", 1.0, (array.get(0) as JsonNumber).value as Double, 0.0)
    }

    @Test
    fun testJsonObjectInstantiation() {
        val obj = JsonObject()

        // Adiciona diferentes tipos de valores
        obj.set("nome", JsonString("João Silva"))
        obj.set("idade", JsonNumber(30.0))
        obj.set("ativo", JsonBoolean(true))
        obj.set("endereco", JsonNull)

        //assertTrue(obj.containsKey("nome"))
        assertTrue(obj.get("nome") is JsonString)
        assertEquals("João Silva", (obj.get("nome") as JsonString).value)
        assertEquals("O valor da idade deveria ser 30.0", 30.0, (obj.get("idade") as JsonNumber).value as Double, 0.0)
        assertTrue((obj.get("ativo") as JsonBoolean).value)
        assertTrue(obj.get("endereco") is JsonNull)
    }

    @Test
    fun testComplexArrayInstantiation() {
        val complexArray = JsonArray()

        // Teste array com diferentes tipos (como em misturas do list1.Json)
        complexArray.add(JsonNumber(42.0))
        complexArray.add(JsonString("texto"))
        complexArray.add(JsonBoolean(true))
        complexArray.add(JsonNumber(3.14))

        assertEquals(4, complexArray.size())
        assertTrue(complexArray.get(0) is JsonNumber)
        assertTrue(complexArray.get(1) is JsonString)
        assertTrue(complexArray.get(2) is JsonBoolean)
        assertTrue(complexArray.get(3) is JsonNumber)
    }

    @Test
    fun testNestedStructuresInstantiation() {
        val objWithArray = JsonObject()
        val array = JsonArray()

        // Criando array aninhado como em list2.Json
        array.add(JsonNumber(1.0))
        array.add(JsonNumber(2.0))

        objWithArray.set("numeros", array)

        assertTrue(objWithArray.get("numeros") is JsonArray)
        val retrievedArray = objWithArray.get("numeros") as JsonArray
        assertEquals(2, retrievedArray.size())
    }
}