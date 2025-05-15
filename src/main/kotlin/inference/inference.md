# Documentação do Pacote `inference`

## Visão Geral

O pacote `inference` fornece funcionalidades para converter automaticamente objetos Kotlin em representações JSON válidas, utilizando reflexão. Esta conversão permite instanciar modelos JSON diretamente a partir de estruturas Kotlin.

---

## Classes

### `JsonInfer` (objeto singleton)

Responsável por realizar a inferência de tipos Kotlin para instâncias do modelo JSON.

#### Método Público

- `fun infer(value: Any?): JsonValue`
  - Ponto de entrada principal para a inferência JSON.
  - Converte qualquer valor Kotlin suportado para o tipo correspondente de `JsonValue`.
  - **Parâmetro:** `value` — Objeto Kotlin (pode ser nulo).
  - **Retorno:** Instância do tipo apropriado de `JsonValue`.

#### Métodos Privados

- `private fun inferList(list: List<*>) : JsonArray`
  - Converte listas Kotlin em arrays JSON.
  - Processa recursivamente cada elemento da lista.
  - **Retorno:** `JsonArray` com os elementos convertidos.

- `private fun inferMap(map: Map<*, *>) : JsonObject`
  - Converte mapas Kotlin em objetos JSON.
  - Apenas processa chaves do tipo `String`.
  - Converte recursivamente os valores.
  - **Retorno:** `JsonObject` com os pares chave-valor convertidos.

- `private fun inferDataClass(obj: Any) : JsonObject`
  - Converte classes de dados (`data class`) Kotlin em objetos JSON.
  - Utiliza reflexão para aceder às propriedades.
  - **Retorno:** `JsonObject` com todas as propriedades inferidas.

---

## Mapeamento de Tipos

| Tipo Kotlin          | Tipo JSON           |
|----------------------|---------------------|
| `null`               | `JsonNull`          |
| `String`             | `JsonString`        |
| `Number` (Int, etc.) | `JsonNumber`        |
| `Boolean`            | `JsonBoolean`       |
| `Enum`               | `JsonString` (nome) |
| `List<*>`            | `JsonArray`         |
| `Map<String, *>`     | `JsonObject`        |
| `Data Class`         | `JsonObject`        |

---

## Exemplo de Utilização

```kotlin
// Tipos básicos
val jsonNumero = JsonInfer.infer(42)
val jsonTexto = JsonInfer.infer("olá")

// Tipos compostos
data class Pessoa(val nome: String, val idade: Int)
val pessoa = Pessoa("João", 30)
val jsonPessoa = JsonInfer.infer(pessoa)