# Documentação do Pacote `model`

## Visão Geral

O pacote `model` fornece os tipos fundamentais de valores JSON e as suas implementações, seguindo a especificação do formato JSON. Estes tipos permitem construir, manipular e serializar estruturas JSON de forma segura e programática.

---

## Classe Base

### `JsonValue` (classe abstrata)
Classe abstrata base para todos os tipos de valores JSON.

#### Métodos abstratos
- `fun serialize(): String`
  - Converte o valor JSON para a sua representação textual no formato padrão JSON.
- `fun accept(visitor: JsonVisitor)`
  - Implementa o padrão Visitor para permitir operações sobre a estrutura.

---

## Classes Concretas

### `JsonObject`
Representa um objeto JSON composto por pares chave-valor.

#### Propriedades
- `properties: MutableMap<String, JsonValue>`

#### Métodos
- `get(key: String): JsonValue?` — Obtém o valor associado à chave.
- `set(key: String, value: JsonValue)` — Define um novo par chave-valor.
- `size(): Int` — Número de propriedades.
- `values(): Collection<JsonValue>` — Lista dos valores armazenados.
- `keys(): Set<String>` — Conjunto das chaves presentes.
- `getKeys(): Set<String>` — Alias de `keys()`.
- `operator fun times(other: JsonObject): JsonObject` — Interseção entre dois objetos.
- `filter(predicate: (String, JsonValue) -> Boolean): JsonObject` — Filtra os pares com base numa condição.

---

### `JsonArray`
Representa um array JSON.

#### Propriedades
- `elements: MutableList<JsonValue>`

#### Métodos
- `add(value: JsonValue)` — Adiciona um elemento.
- `get(index: Int): JsonValue` — Obtém o elemento numa posição.
- `set(index: Int, value: JsonValue)` — Substitui o elemento na posição.
- `size(): Int` — Devolve o tamanho do array.
- `elements(): List<JsonValue>` — Lista imutável com os elementos.
- `filter(predicate: (JsonValue) -> Boolean): JsonArray` — Filtra elementos.
- `map(transform: (JsonValue) -> JsonValue): JsonArray` — Aplica uma transformação.

---

### `JsonString`
Representa um valor textual (string) em JSON.

#### Propriedades
- `value: String`

#### Métodos
- `serialize()` — Trata o escape de caracteres especiais.
- `equals()`, `hashCode()` — Garantem igualdade com base no conteúdo da string.

---

### `JsonNumber`
Representa um valor numérico JSON (inteiro ou decimal).

#### Propriedades
- `value: Number`

#### Métodos
- `serialize()` — Serializa o número, removendo casas decimais se for inteiro.

---

### `JsonBoolean`
Representa um valor booleano (true/false).

#### Propriedades
- `value: Boolean`

#### Métodos
- `serialize()` — Devolve `"true"` ou `"false"`.

---

### `JsonNull`
Representa o valor `null` em JSON.

#### Tipo
- Objeto Singleton

#### Métodos
- `serialize()` — Devolve `"null"`.

---

## Funcionalidades Comuns

Todas as classes:
- São serializáveis para o formato JSON válido.
- Suportam o padrão Visitor.
- Permitem operações seguras e coesas.
- Promovem a imutabilidade lógica dos dados.

---

## Exemplo de Utilização

```kotlin
// Criar um objeto JSON
val person = JsonObject().apply {
    set("name", JsonString("João"))
    set("age", JsonNumber(30))
    set("address", JsonObject().apply {
        set("city", JsonString("Lisboa"))
    })
}

// Serializar para string JSON
val json = person.serialize()
