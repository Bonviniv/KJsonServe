
package test

import model.*

fun main() {
    val numeros = JsonArray(
        listOf(
            JsonNumber(1),
            JsonNumber(2),
            JsonNumber(3.5),
            JsonNumber(10)
        )
    )

    println(numeros.serialize())
    // Esperado: [1,2,3.5,10]
}
