# Projeto de Programação Avançada — JSON & GetJson Framework

**Mestrado em Engenharia Informática – ISCTE 2024/2025**  
Autores: **Vítor Barbosa (105248)** e **Paulo Francisco Pinto (128962)**

---

## 📦 Visão Geral

Este projeto implementa:

- Uma **biblioteca JSON em Kotlin**, que permite representar, manipular e serializar estruturas JSON inteiramente em memória.
- Um **microframework HTTP (`GetJson`)**, que expõe endpoints `GET` com retorno automático em JSON a partir de métodos Kotlin, usando anotações personalizadas.

---

## ✅ Módulo 1 — Biblioteca JSON (Modelo em Memória)

- Hierarquia de tipos: `JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`.
- Suporte a serialização para o formato JSON padrão (`serialize()`).
- Operações funcionais:
  - `filter`, `map`, `times` (interseção de objetos)
- Validações via padrão Visitor:
  - `ValidatorVisitor`: verifica se as chaves dos objetos são válidas.
  - `ArrayHomogeneityVisitor`: verifica se os elementos de um array são do mesmo tipo (ignorando `null`).

---

## 🧠 Módulo 2 — Inferência com Reflexão

- Função `JsonInfer.infer(value: Any?)`:
  - Converte automaticamente objetos Kotlin em `JsonValue`.
- Suporta:
  - Tipos primitivos (`Int`, `Double`, `Boolean`, `String`)
  - `List<T>`, `Map<String, T>`
  - `Enum`, `null`
  - `data class` com campos de tipos suportados

---

## 🌐 Módulo 3 — Microframework HTTP `GetJson`

- Framework simples para criação de endpoints `GET` com anotações:
  - `@Mapping("rota")`: define o caminho da rota
  - `@Path`: parâmetros dinâmicos no caminho (e.g. `/user/{id}`)
  - `@Param`: parâmetros da query string (`?nome=joao`)
- Conversão automática da resposta para JSON usando `JsonInfer`.

---

## ⚙️ Instalação via Maven

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

## 🚀 Exemplos de Utilização

### 1. Criação Manual de JSON
```kotlin
val obj = JsonObject().apply {
    set("nome", JsonString("Alice"))
    set("idade", JsonNumber(30.0))
    set("ativo", JsonBoolean(true))
}
println(obj.serialize())
// {"nome":"Alice","idade":30,"ativo":true}
```

### 2. Filtrar e Mapear Arrays
```kotlin
val arr = JsonArray().apply {
    add(JsonNumber(1.0))
    add(JsonNumber(2.0))
    add(JsonNumber(3.0))
}

val pares = arr.filter { (it as JsonNumber).value.toDouble() % 2 == 0.0 }
println(pares.serialize()) // [2]

val duplicado = arr.map {
    if (it is JsonNumber) JsonNumber((it.value as Number).toDouble() * 2) else it
}
println(duplicado.serialize()) // [2, 4, 6]
```

### 3. Uso de Visitors
```kotlin
val validator = ValidatorVisitor()
obj.accept(validator)
println("É válido? ${validator.isValid()}")

val homog = ArrayHomogeneityVisitor()
arr.accept(homog)
println("É homogéneo? ${homog.isHomogeneous()}")
```

### 4. Inferência com Kotlin
```kotlin
data class Person(val name: String, val age: Int)

val json = JsonInfer.infer(Person("João", 25))
println(json.serialize())
// {"name":"João","age":25}
```

### 5. Servidor HTTP com GetJson
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

// Iniciar servidor
val app = GetJson(Controller::class)
app.start(8080)
```

---

## 📚 Referência da API

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

## ✅ Testes Automatizados

```bash
mvn test
```

---

## 📦 Compilar JAR

```bash
mvn clean package
mv target/ProjetoPA-1.0.0.jar release/ProjetoPA-1.0.0.jar
```

---

## 📁 Estrutura do Projeto

```
ProjetoPA/
├───.idea
│   ├───codeStyles
│   └───libraries
├───docs
│   └───diagrams
├───lib
├───release
├───src
│   ├───main
│   │   ├───kotlin
│   │   │   ├───getjson
│   │   │   │   └───annotations
│   │   │   ├───inference
│   │   │   ├───model
│   │   │   └───visitor
│   │   └───resources
│   └───test
│       ├───kotlin
│       │   ├───getjson
│       │   ├───Jsons
│       │   └───test
│       └───resources
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
    ├───maven-status
    │   └───maven-compiler-plugin
    │       └───compile
    │           └───default-compile
    └───test-classes
        ├───getjson
        ├───Jsons
        ├───META-INF
        └───test

```

---

## 👥 Autores

- **Vítor Barbosa** (105248)
- **Paulo Francisco Pinto** (128962)

Projeto realizado no âmbito da unidade curricular **Programação Avançada**,  
**Mestrado em Engenharia Informática — ISCTE, 2024/2025**
