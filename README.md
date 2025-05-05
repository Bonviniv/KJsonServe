# PA_projeto
**Projeto de PA (2025)**  
*Vitor Barbosa (105248) e Paulo Francisco Pinto (128962)*

---

# JSON Manipulation Library & Mini HTTP Framework (`GetJson`)

Biblioteca Kotlin para geração e manipulação de JSON em memória, sem dependências externas (exceto JUnit para testes) e com suporte a APIs HTTP simples via anotação.

---

## 📖 Visão Geral

Este projeto oferece:

### ✅ JSON In-Memory Model
- `JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`.
- Suporte a serialização JSON padrão.
- Operações funcionais: `filter`, `map`, `*` (interseção), etc.
- Validação por Visitor:
  - `ValidatorVisitor`: valida unicidade e formato das chaves.
  - `ArrayHomogeneityVisitor`: valida homogeneidade de tipos num array.

### ✅ Inferência via Reflexão
- `JsonInfer.infer(...)`: converte objetos Kotlin em modelos JSON usando reflexão.
- Suporta: primitivos, `List`, `Map`, `Enum`, `Data class`, `null`.

### ✅ Mini Framework `GetJson`
- Framework de rotas HTTP `GET` via anotação:
  - `@Mapping`: mapeia classes e métodos para endpoints.
  - `@Path`: mapeia partes da URL (ex: `/path/{id}`).
  - `@Param`: extrai parâmetros da query string (`?n=1&text=foo`).
- Conversão automática do resultado para JSON com `JsonInfer`.

---

## ⚙️ Instalação (Maven)

Adicione ao seu `pom.xml`:

```xml
<properties>
  <kotlin.version>1.9.25</kotlin.version>
</properties>

<dependencies>
  <!-- Kotlin Standard Library -->
  <dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-stdlib</artifactId>
    <version>${kotlin.version}</version>
  </dependency>

  <!-- Kotlin Reflection (para inferência) -->
  <dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-reflect</artifactId>
    <version>${kotlin.version}</version>
  </dependency>

  <!-- JUnit para testes -->
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

---

## 🚀 Quickstart

### 1. Criação Manual
```kotlin
import model.*

val obj = JsonObject().apply {
  set("nome", JsonString("Alice"))
  set("idade", JsonNumber(30.0))
  set("ativo", JsonBoolean(true))
}
println(obj.serialize())
// Saída: {"nome":"Alice","idade":30,"ativo":true}
```

### 2. Filtrar e Mapear Arrays
```kotlin
val arr = JsonArray().apply {
  add(JsonNumber(1.0))
  add(JsonNumber(2.0))
  add(JsonNumber(3.0))
}

val even = arr.filter { (it as JsonNumber).value.toDouble() % 2 == 0.0 }
println(even.serialize()) // [2]

val doubled = arr.map {
  if (it is JsonNumber) JsonNumber((it.value as Number).toDouble() * 2) else it
}
println(doubled.serialize()) // [2,4,6]
```

### 3. Uso de Visitors
```kotlin
val validator = ValidatorVisitor()
obj.accept(validator)
println("Válido? ${validator.isValid()}")

val homog = ArrayHomogeneityVisitor()
arr.accept(homog)
println("Homogêneo? ${homog.isHomogeneous()}")
```

### 4. Inferência via Reflexão
```kotlin
data class Person(val name: String, val age: Int)
val inferred = JsonInfer.infer(Person("Bob", 25))
println(inferred.serialize())
// {"name":"Bob","age":25}
```

### 5. Framework HTTP `GetJson`
```kotlin
@Mapping("api")
class Controller {
  @Mapping("ints")
  fun demo(): List<Int> = listOf(1, 2, 3)

  @Mapping("path/{id}")
  fun dynamic(@Path id: String): String = "$id!"

  @Mapping("args")
  fun args(@Param("n") n: Int, @Param("text") text: String): Map<String, String> =
    mapOf(text to text.repeat(n))
}

// Lançar servidor
val app = GetJson(Controller::class)
app.start(8080)
```

---

## 📑 API Reference

### `JsonValue` (abstract)
- `serialize(): String`
- `accept(visitor: JsonVisitor)`

### `JsonObject`
- `set(key: String, value: JsonValue)`
- `get(key: String): JsonValue?`
- `filter((String, JsonValue) -> Boolean): JsonObject`
- `operator fun times(other: JsonObject): JsonObject`
- `getKeys(): Set<String>`

### `JsonArray`
- `add(value: JsonValue)`
- `get(index: Int): JsonValue`
- `size(): Int`
- `filter((JsonValue) -> Boolean): JsonArray`
- `map((JsonValue) -> JsonValue): JsonArray`

### Visitors
- `ValidatorVisitor.isValid()`
- `ArrayHomogeneityVisitor.isHomogeneous()`

### Inferência
- `JsonInfer.infer(value: Any?): JsonValue`

---

## 🧪 Testes
```bash
mvn test
```

---

## 🧰 Build JAR
```bash
mvn clean package
mv target/ProjetoPA-1.0.0.jar release/ProjetoPA-1.0.0.jar
```

---

## 📁 Project Structure

```
ProjetoPA/
├── src/
│   ├── main/kotlin/
│   │   ├── model/
│   │   ├── visitor/
│   │   ├── inference/
│   │   └── getjson/
│   └── test/kotlin/
│       └── getjson/
├── docs/diagrams/JsonModelDiagram.png
├── release/ProjetoPA-1.0.0.jar
└── pom.xml
```

---

## 👥 Autores

- Vítor Barbosa (105248)
- Paulo Francisco Pinto (128962)

Projeto desenvolvido no âmbito da unidade curricular **Programação Avançada** (Mestrado em Engenharia Informática – ISCTE 2024/2025).
