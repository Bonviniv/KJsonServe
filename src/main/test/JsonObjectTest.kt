
package test

import model.*

fun main() {
    val pessoa = JsonObject(
        mapOf(
            "nome" to JsonString("Vitor"),
            "idade" to JsonNumber(25),
            "ativo" to JsonBoolean(true),
            "email" to JsonNull,
            "hobbies" to JsonArray(
                listOf(
                    JsonString("programar"),
                    JsonString("jogar"),
                    JsonString("ler")
                )
            )
        )
    )

    println(pessoa.serialize())
    // Esperado:
    // {"nome":"Vitor","idade":25,"ativo":true,"email":null,"hobbies":["programar","jogar","ler"]}
}
