# 🔍 Resumo dos Conceitos que o prof falou em aula

## 1. Serialização
- Transformar uma estrutura de dados (como objetos em memória) para uma representação textual que possa ser salva ou transmitida.
- **Exemplo**: Converter um objeto Kotlin para uma string JSON válida.

## 2. Representação
- Refere-se à estrutura em memória dos dados (neste caso, classes como `JsonObject`, `JsonArray`, etc.).
- É o modelo interno usado para manipular JSON.

## 3. Visualização
- A apresentação dos dados para o utilizador final, geralmente em interfaces gráficas.
- **Não será utilizada neste projeto.**

> 🟡 **Importante**: o projeto foca em **representação** e **serialização**, **sem visualização**.

---

# 🧱 Estrutura do Projeto Passo a Passo

---

## ✅ Fase 1 – Modelo JSON (Representação + Serialização)

### 1. Criar Hierarquia de Classes para o Modelo
Cria uma `sealed class` base `JsonValue`, com subclasses:
- `JsonObject` – mapa de chaves (strings) para valores JSON.
- `JsonArray` – lista de `JsonValue`.
- `JsonString`
- `JsonNumber`
- `JsonBoolean`
- `JsonNull`

### 2. Implementar Métodos Básicos
Cada tipo precisa de:
- Um método `serialize()` que retorna uma `String` no formato JSON padrão.
- Métodos para acessar/modificar elementos (por exemplo, `get`, `set`, etc.).

### 3. Filtragem
- `JsonObject.filter(predicate: (String, JsonValue) -> Boolean): JsonObject`
- `JsonArray.filter(predicate: (JsonValue) -> Boolean): JsonArray`

### 4. Mapeamento
- `JsonArray.map(transform: (JsonValue) -> JsonValue): JsonArray`

### 5. Visitor Pattern
Cria uma interface `JsonVisitor` com um método para cada tipo (`visitObject`, `visitArray`, etc.).

Utiliza visitantes para implementar:
- Validação de `JsonObject` (chaves únicas, valores válidos).
- Verificação de homogeneidade de `JsonArray` (mesmo tipo, excluindo `JsonNull`).

### 6. Serialização
- Cada tipo implementa um `serialize(): String` conforme a [especificação JSON](https://www.json.org/).

---

## ✅ Fase 2 – Inferência com Reflexão

### 1. Função Principal
- `fun infer(value: Any?): JsonValue` – transforma objetos Kotlin em estruturas `JsonValue`.

### 2. Tipos Suportados
- Primitivos (`Int`, `Double`, `Boolean`, `String`)
- `List<T>`
- `Enum`
- `Map<String, T>`
- `Data classes`

### 3. Uso de Reflexão
- Usar `KClass` e `memberProperties` para acessar atributos de data classes.
- Converter cada propriedade recursivamente para `JsonValue`.

### 4. Exemplo
Transformar a instância `Course(...)` no JSON conforme o exemplo no enunciado.

### 5. Validação
- Garantir que o objeto inferido seja um `JsonObject` e mantenha a estrutura conforme as regras.

---

## ✅ Fase 3 – (A Definir)
Aguardar instruções futuras. Provavelmente alguma aplicação prática da biblioteca.

---

# 🧪 Testes & Documentação

- `KDoc` para todas as classes e métodos.
- `JUnit` para cada funcionalidade:
    - Serialização de cada tipo
    - Filtragem e mapeamento
    - Uso de visitors
    - Inferência com diferentes tipos

---

# 📁 Repositório GitHub
[https://github.com/Bonviniv/ProjetoPA](https://github.com/Bonviniv/ProjetoPA)

Inclui:
- Código fonte
- Diagrama UML com as classes JSON
- JAR com release
- Tutorial de uso básico com exemplos