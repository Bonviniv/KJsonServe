# Plano e Organização do Projeto

## 🔍 Conceitos Fundamentais

### 1. Serialização
Transformação de uma estrutura de dados (em memória) para uma representação textual padronizada (neste caso, JSON).  
**Exemplo:** Converter um objeto Kotlin para uma `String` em formato JSON.

### 2. Representação
Estrutura de classes que modela o conteúdo JSON na aplicação, como `JsonObject`, `JsonArray`, `JsonString`, etc.

### 3. Visualização
Exibição dos dados ao utilizador final (GUI, Web, etc.).  
**Nota:** Não faz parte deste projeto.

> **Resumo:** Este projeto foca-se em **representação** e **serialização**, sem visualização.

---

## 🧱 Estrutura do Projeto por Fases

---

## ✅ Fase 1 – Modelo JSON (Representação + Serialização)

### Objetivos
- Criar hierarquia de classes que representa os tipos JSON.
- Implementar métodos de manipulação e serialização.

### Estrutura
- `JsonValue` (classe abstrata base)
- Subclasses:
  - `JsonObject` — mapa de chaves para valores JSON.
  - `JsonArray` — lista de valores JSON.
  - `JsonString`
  - `JsonNumber`
  - `JsonBoolean`
  - `JsonNull`

### Funcionalidades Implementadas
- `serialize(): String` para cada tipo, gerando JSON válido.
- Métodos auxiliares: `add`, `get`, `set`, `size`, etc.
- `filter`:
  - `JsonObject.filter(predicate: (String, JsonValue) -> Boolean)`
  - `JsonArray.filter(predicate: (JsonValue) -> Boolean)`
- `map`:
  - `JsonArray.map(transform: (JsonValue) -> JsonValue)`
- **Padrão Visitor**:
  - Interface `JsonVisitor` com métodos para cada tipo.
  - Visitantes:
    - `ValidatorVisitor`: valida chaves e estrutura.
    - `ArrayHomogeneityVisitor`: verifica homogeneidade de tipos num `JsonArray`.

---

## ✅ Fase 2 – Inferência com Reflexão

### Objetivo
Instanciar o modelo JSON a partir de estruturas Kotlin usando reflexão.

### Função Principal
- `fun infer(value: Any?): JsonValue`

### Tipos Suportados
- Primitivos: `Int`, `Double`, `Boolean`, `String`
- `Enum`
- `List<T>` (recursiva)
- `Map<String, T>`
- `data class` com propriedades suportadas

### Implementação
- Uso de `KClass` e `memberProperties` para ler propriedades de data classes.
- Conversão recursiva para `JsonValue`.

### Exemplo
```kotlin
val course = Course(...)
val json = JsonInfer.infer(course)