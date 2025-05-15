# Biblioteca de Manipulação de JSON

## Programação Avançada — Projeto 2024/2025

O objetivo é desenvolver uma biblioteca em Kotlin para criar e manipular estruturas JSON em memória, suportando todos os tipos de valores definidos pela especificação:

- Objetos (`JsonObject`)
- Arrays (`JsonArray`)
- Strings (`JsonString`)
- Números (`JsonNumber`)
- Booleanos (`JsonBoolean`)
- Nulo (`JsonNull`)

> ⚠️ Nota: A biblioteca **não trata do parsing** de texto JSON (ler strings para JSON). O foco está na **manipulação em memória** e serialização para strings compatíveis com JSON.

> ⚠️ Apenas é permitido o uso de bibliotecas externas para testes com `JUnit`.

---

## ✅ Fase 1 — Modelo JSON (Representação + Serialização)

### Objetivo
Desenvolver classes que representam os tipos de valor JSON, permitindo:

- Criação programática de objetos JSON
- Operações de filtragem e transformação
- Serialização para strings JSON válidas

### Funcionalidades Requeridas
- Instanciar objetos `JsonValue` e compô-los
- Filtrar:
  - `JsonObject → JsonObject`
  - `JsonArray → JsonArray`
- Mapear:
  - `JsonArray.map(...) → JsonArray`
- Suportar o padrão Visitor:
  - Validar objetos JSON (chaves válidas e únicas)
  - Verificar se arrays são homogéneos (mesmo tipo, exceto `null`)
- Serializar todos os tipos para strings válidas em formato JSON

---

## ✅ Fase 2 — Inferência com Reflexão

### Objetivo
Criar uma função que converte objetos Kotlin em estruturas da biblioteca JSON.

### Requisitos
- Suportar os seguintes tipos Kotlin:
  - `Int`, `Double`, `Boolean`, `String`
  - `Enum`
  - `List<T>` (recursivo)
  - `Map<String, T>`
  - `data class` com propriedades suportadas
  - `null`

### Exemplo

```kotlin
data class Course(
    val name: String,
    val credits: Int,
    val evaluation: List<EvalItem>
)

data class EvalItem(
    val name: String,
    val percentage: Double,
    val mandatory: Boolean,
    val type: EvalType?
)

enum class EvalType {
    TEST, PROJECT, EXAM
}

val course = Course(
    "PA", 6,
    listOf(
        EvalItem("quizzes", 0.2, false, null),
        EvalItem("project", 0.8, true, EvalType.PROJECT)
    )
)

val json = JsonInfer.infer(course)
println(json.serialize())