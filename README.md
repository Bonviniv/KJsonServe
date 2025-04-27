# PA_projeto  
**Projeto de PA (2025)**  
*Vitor Barbosa (105248) e Paulo Francisco Pinto (128962)*  


# JSON Manipulation Library
Biblioteca Kotlin para geração e manipulação de JSON em memória, sem dependências externas (exceto JUnit para testes).

---
## 📖 Visão Geral
Esta biblioteca oferece:

- **Modelos em Memória**
  - `JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`.
- **Serialização**
  - Converte qualquer modelo JSON em uma string válida (padrão JSON).
- **Operações Funcionais**
  - `filter` e `map` em arrays (`JsonArray`).
  - `filter` em objetos (`JsonObject`).
  - Operador `*` para interseção de objetos JSON.
- **Visitors**
  - `ValidatorVisitor`: valida chaves únicas e não-vazias em `JsonObject`.
  - `ArrayHomogeneityVisitor`: garante que elementos não-null em cada `JsonArray` sejam do mesmo tipo.
- **Inferência de Objetos Kotlin**
  - `JsonInfer.infer(...)`: converte valores Kotlin (primitivos, listas, mapas, enums, data classes) em modelos `JsonValue`.

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

Após isso, execute:

```bash
mvn clean package
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
import model.*

val arr = JsonArray().apply {
  add(JsonNumber(1.0))
  add(JsonNumber(2.0))
  add(JsonNumber(3.0))
}

// Filtrar números pares\ nval even = arr.filter { (it as JsonNumber).value.toDouble() % 2 == 0.0 }
println(even.serialize())
// Saída: [2]

// Duplicar valores
val doubled = arr.map {
  if (it is JsonNumber) JsonNumber((it.value as Number).toDouble() * 2) else it
}
println(doubled.serialize())
// Saída: [2,4,6]
```

### 3. Uso de Visitors
```kotlin
import visitor.*

// ValidatorVisitor
val validator = ValidatorVisitor()
obj.accept(validator)
println("Válido? ${validator.isValid()}")

// ArrayHomogeneityVisitor
val homog = ArrayHomogeneityVisitor()
arr.accept(homog)
println("Homogêneo? ${homog.isHomogeneous()}")
```

### 4. Inferência via Reflexão
```kotlin
import inference.JsonInfer

data class Person(val name: String, val age: Int)

val inferred = JsonInfer.infer(Person("Bob", 25))
println(inferred.serialize())
// Saída: {"name":"Bob","age":25}
```

---
## 📑 API Reference

### Classe Abstrata `JsonValue`
- `abstract fun serialize(): String`
- `abstract fun accept(visitor: JsonVisitor)`

### `JsonObject`
- `fun set(key: String, value: JsonValue)`
- `fun get(key: String): JsonValue?`
- `fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject`
- `fun serialize(): String`
- `operator fun times(other: JsonObject): JsonObject` // Added this line
- `fun getKeys(): Set<String>` // Added this line

### `JsonArray`
- `fun add(value: JsonValue)`
- `fun get(index: Int): JsonValue`
- `fun size(): Int`
- `fun filter(predicate: (JsonValue) -> Boolean): JsonArray`
- `fun map(transform: (JsonValue) -> JsonValue): JsonArray`
- `fun serialize(): String`

### `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull` (subclasses de `JsonValue`)

### `interface JsonVisitor`
Métodos: `visitObject`, `visitArray`, `visitString`, `visitNumber`, `visitBoolean`, `visitNull`.

### Visitors:
- `ValidatorVisitor`: `fun isValid(): Boolean`
- `ArrayHomogeneityVisitor`: `fun isHomogeneous(): Boolean`

### Inferência
- `object JsonInfer`:
  - `fun infer(value: Any?): JsonValue`

---
## 🔧 Testes
Execute todos os testes:
```bash
mvn test
```

---
## 📁 Project Structure
```markdown
ProjetoPA/
├── src/
│   ├── main/
│   │   └── kotlin/
│   │       ├── model/
│   │       │   ├── JsonArray.kt
│   │       │   ├── JsonBoolean.kt
│   │       │   ├── JsonNull.kt
│   │       │   ├── JsonNumber.kt
│   │       │   ├── JsonObject.kt
│   │       │   ├── JsonString.kt
│   │       │   ├── JsonValue.kt
│   │       │   └── JsonVisitor.kt
│   │       ├── visitor/
│   │       │   ├── ArrayHomogeneityVisitor.kt
│   │       │   └── ValidatorVisitor.kt
│   │       └── inference/
│   │           └── JsonInfer.kt
│   └── test/
│       └── kotlin/
│           └── test/
│               ├── Jsons/
│               │   └── (JSON test files)
│               ├── TestsPhase1.kt
│               ├── TestsPhase2.kt
│               ├── JsonBooleanTests.kt
│               ├── JsonNullTests.kt
│               ├── JsonNumberTests.kt
│               ├── JsonStringTests.kt
│               └── ValidatorVisitorTests.kt
└── pom.xml
```

---
## 🤝 Contribuição
Sinta-se livre para abrir _issues_ ou _pull requests_.

---
*Projeto desenvolvido para a disciplina de Programação Avançada, Mestrado em Engenharia Informática no ISCTE IUL - Lisboa @ 2025.*
