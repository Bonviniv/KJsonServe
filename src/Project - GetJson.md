# GetJson

## Programação Avançada — Projeto 2024/2025

O objetivo desta componente é desenvolver uma pequena framework HTTP, em Kotlin, que permite definir endpoints do tipo `GET` que retornam JSON, de forma semelhante ao Spring Boot.

Esta framework usa a biblioteca JSON desenvolvida nas fases anteriores para converter automaticamente valores Kotlin em JSON, sem necessidade de construir manualmente as strings.

---

## Requisitos

- Apenas são permitidas bibliotecas externas para testes:
    - `JUnit`
    - Uma biblioteca de cliente HTTP (ex.: [`OkHttp`](https://square.github.io/okhttp/))
- A framework deve:
    - Ser inicializada com uma lista de classes controladoras.
    - Usar anotações para mapear rotas (`@Mapping`) e argumentos (`@Path`, `@Param`).
    - Suportar `GET` requests apenas.
    - Converter automaticamente o valor de retorno em JSON.

---

## Exemplo de Utilização

```kotlin
fun main() {
    val app = GetJson(Controller::class)
    app.start(8080)
}

@Mapping("api")
class Controller {

    @Mapping("ints")
    fun demo(): List<Int> = listOf(1, 2, 3)

    @Mapping("pair")
    fun obj(): Pair<String, String> = Pair("um", "dois")

    @Mapping("path/{pathvar}")
    fun path(@Path pathvar: String): String = pathvar + "!"

    @Mapping("args")
    fun args(
        @Param n: Int,
        @Param text: String
    ): Map<String, String> = mapOf(text to text.repeat(n))
}