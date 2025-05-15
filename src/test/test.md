# Documentação dos Testes (`test`)

## Visão Geral

O pacote `test` contém um conjunto completo de testes para validar o funcionamento da biblioteca JSON: criação de valores, serialização, visitantes, inferência e integração com o servidor HTTP.

---

## Conjuntos de Testes

### `TestsPhase1`
Testes básicos da Fase 1: serialização e manipulação de estruturas JSON.

#### Métodos Testados
- `testBasicValues` — Serialização de tipos básicos: string, número, booleano e nulo.

---

### `TestsPhase2`
Testes simples relacionados com a construção manual de objetos JSON.

#### Métodos Testados
- `testComplexStructures` — Verifica a criação e serialização de um `JsonObject`.

---

### `JsonObjectTest`
Testa as funcionalidades de objetos JSON.

#### Métodos Testados
- `testCreateEmptyObject` — Criação de objeto vazio.
- `testPutAndGetValue` — Inserção e leitura de chaves/valores.
- `testSize` — Contagem de propriedades.
- `testSerialize` — Serialização completa.
- `testAccept` — Aplicação de visitante.
- `testJsonObjectFilter` — Filtragem de propriedades.

---

### `JsonArrayTest`
Testes de operações sobre arrays JSON.

#### Métodos Testados
- `testCreateEmptyArray` — Criação de array vazio.
- `testAddAndGetElement` — Inserção e acesso a elementos.
- `testSetElement` — Modificação de elementos.
- `testSize` — Verificação de tamanho.
- `testFilter` — Filtragem por tipo.
- `testMap` — Transformações dos elementos.
- `testSerialize` — Serialização para string.
- `testAccept` — Suporte ao padrão Visitor.

---

### `JsonStringTests`, `JsonNumberTests`, `JsonBooleanTests`, `JsonNullTests`
Testes de tipos primitivos JSON.

#### Métodos Testados
- `testStringValue` — Verifica valor e serialização de `JsonString`.
- `testNumberValue` — Verifica valores inteiros e decimais.
- `testBooleanValue` — Serialização de booleanos.
- `testNullSerialization` — Serialização de valor nulo.

---

### `JsonInstantiationTests`
Testes de instanciamento manual dos tipos básicos JSON.

#### Métodos Testados
- `testInstantiation` — Criação de `JsonString`, `JsonNumber`, `JsonBoolean`.

---

### `JsonInferTests`
Testes sobre tipo de dados armazenado em `JsonObject`.

#### Métodos Testados
- `testBasicInference` — Verifica tipo interno de `JsonString`.

---

### `ValidatorVisitorTests`
Testes de validação de objetos JSON.

#### Métodos Testados
- `testValidObject` — Verifica que objetos com chaves válidas são aceites.

---

### `ArrayHomogeneityVisitorTests`
Testes de verificação de homogeneidade em arrays.

#### Métodos Testados
- `testHomogeneousArray` — Garante que todos os elementos são do mesmo tipo.

---

### `AllTests`
Agrupador que executa todos os testes do projeto.

---

## Observações

- Os testes utilizam **JUnit 4**.
- Cada classe de teste cobre funcionalidades específicas do modelo ou da framework.
- Os testes de integração HTTP estão na pasta `getjson`.