# Advanced Programming Project — JSON & GetJson Framework

**Master's in Informatics Engineering – ISCTE 2024/2025**  
Authors: **Vítor Barbosa (105248)** and **Paulo Francisco Pinto (128962)**

---

## 📦 Overview

This project implements:

- A **JSON library in Kotlin**, allowing the representation, manipulation, and serialization of JSON structures entirely in memory.
- An **HTTP microframework (`GetJson`)**, which exposes `GET` endpoints that automatically return JSON from Kotlin methods using custom annotations.

---

## ✅ Module 1 — JSON Library (In-Memory Model)

- Type hierarchy: `JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`.
- Support for serialization to standard JSON format (`serialize()`).
- Functional operations:
  - `filter`, `map`, `times` (object intersection)
- Validations via Visitor pattern:
  - `ValidatorVisitor`: checks if object keys are valid.
  - `ArrayHomogeneityVisitor`: checks if array elements are of the same type (ignoring `null`).

---

## 🧠 Module 2 — Inference with Reflection

- Function `JsonInfer.infer(value: Any?)`:
  - Automatically converts Kotlin objects into `JsonValue`.
- Supports:
  - Primitive types (`Int`, `Double`, `Boolean`, `String`)
  - `List<T>`, `Map<String, T>`
  - `Enum`, `null`
  - `data class` with fields of supported types

---

## 🌐 Module 3 — HTTP Microframework `GetJson`

- Simple framework for creating `GET` endpoints with annotations:
  - `@Mapping("route")`: defines the route path
  - `@Path`: dynamic path parameters (e.g. `/user/{id}`)
  - `@Param`: query string parameters (`?name=joao`)
- Automatic conversion of response to JSON using `JsonInfer`.

---

## ⚙️ Installation via Maven

```xml
<properties>
  <kotlin.version>1.9.25</kotlin.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-stdlib</artifactId>
    <version>${kotlin.version}</version>
  </dependency>

  <dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-reflect</artifactId>
    <version>${kotlin.version}</version>
  </dependency>

  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

---

## 🚀  Usage Examples

### 1. Manual JSON Creation
```kotlin
val obj = JsonObject().apply {
    set("name", JsonString("Alice"))
    set("age", JsonNumber(30.0))
    set("active", JsonBoolean(true))
}
println(obj.serialize())
// {"name":"Alice","age":30,"active":true}

```

### 2. Filtering and Mapping Arrays
```kotlin
val arr = JsonArray().apply {
    add(JsonNumber(1.0))
    add(JsonNumber(2.0))
    add(JsonNumber(3.0))
}

val evens = arr.filter { (it as JsonNumber).value.toDouble() % 2 == 0.0 }
println(evens.serialize()) // [2]

val doubled = arr.map {
    if (it is JsonNumber) JsonNumber((it.value as Number).toDouble() * 2) else it
}
println(doubled.serialize()) // [2, 4, 6]

```

### 3. Using Visitors
```kotlin
val validator = ValidatorVisitor()
obj.accept(validator)
println("Is valid? ${validator.isValid()}")

val homog = ArrayHomogeneityVisitor()
arr.accept(homog)
println("Is homogeneous? ${homog.isHomogeneous()}")

```

### 4. Inference with Kotlin
```kotlin
data class Person(val name: String, val age: Int)

val json = JsonInfer.infer(Person("João", 25))
println(json.serialize())
// {"name":"João","age":25}

```

### 5. HTTP Server with GetJson
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

// Start server
val app = GetJson(Controller::class)
app.start(8080)

```

---

## 📚 API Reference

### JsonValue
- `serialize()`, `accept(visitor)`

### JsonObject
- `set`, `get`, `filter`, `times`, `getKeys`

### JsonArray
- `add`, `get`, `size`, `filter`, `map`

### Visitors
- `ValidatorVisitor.isValid()`
- `ArrayHomogeneityVisitor.isHomogeneous()`

### Inferência
- `JsonInfer.infer(value: Any?)`

---

## ✅ Automated Tests

```bash
mvn test
```

---

## 📦 Build JAR

```bash
mvn clean package
mv target/ProjetoPA-1.0.0.jar release/ProjetoPA-1.0.0.jar
```

---

## 📁 Project Structure

```
ProjetoPA/
├───.idea
│   ├───artifacts
│   └───codeStyles
├───docs
│   └───diagrams
├───lib
├───out
│   └───artifacts
│       └───ProjetoPA_jar   <-- updated .jar
├───release
├───src
│   ├───main
│   │   └───kotlin
│   │       ├───getjson
│   │       │   └───annotations
│   │       ├───inference
│   │       ├───model
│   │       └───visitor
│   └───test
│       └───kotlin
│           ├───getjson
│           ├───Jsons
│           └───test
└───target
    ├───classes
    │   ├───getjson
    │   │   └───annotations
    │   ├───inference
    │   ├───META-INF
    │   ├───model
    │   └───visitor
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    └───test-classes
        ├───getjson
        ├───META-INF
        └───test


```

---

## 👥 Authors

- **Vítor Barbosa** (105248)
- **Paulo Francisco Pinto** (128962)

Project carried out for the **Advanced Programming** course,
**Master's in Informatics Engineering — ISCTE, 2024/2025**

