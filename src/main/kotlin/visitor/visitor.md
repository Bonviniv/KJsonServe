# Documentação do Pacote `visitor`

## Visão Geral

O pacote `visitor` implementa o padrão Visitor para percorrer e validar estruturas JSON. Este padrão permite separar a lógica de processamento dos dados propriamente ditos, facilitando a extensão de comportamentos (como validações ou transformações) sem modificar as classes do modelo JSON.

---

## Interface Base

### `JsonVisitor`
Interface que define os métodos de visita para cada tipo de valor JSON.

#### Métodos
- `visitObject(jsonObject: JsonObject)`
  - Visita objetos JSON.
- `visitArray(jsonArray: JsonArray)`
  - Visita arrays JSON.
- `visitString(jsonString: JsonString)`
  - Visita valores string.
- `visitNumber(jsonNumber: JsonNumber)`
  - Visita valores numéricos.
- `visitBoolean(jsonBoolean: JsonBoolean)`
  - Visita valores booleanos.
- `visitNull(jsonNull: JsonNull)`
  - Visita valores nulos.

---

## Visitantes Concretos

### `ArrayHomogeneityVisitor`
Verifica se todos os elementos de um array JSON são do mesmo tipo (exceto nulos).

#### Propriedades
- `private var currentType: Class<*>?`
  - Regista o tipo do primeiro elemento do array.
- `private var homogeneous: Boolean`
  - Indica se os elementos são homogéneos.

#### Métodos
- `fun isHomogeneous(): Boolean`
  - Devolve `true` se todos os elementos são do mesmo tipo.

#### Implementação de visitas
- `visitArray`: percorre os elementos e verifica se o tipo é consistente.
- Os outros métodos (`visitObject`, `visitString`, etc.): não afetam a verificação.

---

### `ValidatorVisitor`
Valida a integridade estrutural de um modelo JSON.

#### Propriedades
- `private var valid: Boolean`
  - Indica se o modelo é considerado válido.

#### Métodos
- `fun isValid(): Boolean`
  - Devolve `true` se o modelo passou na validação.

#### Implementação de visitas
- `visitObject`: verifica se todas as chaves são não vazias e visita recursivamente os valores.
- `visitArray`: visita recursivamente todos os elementos do array.
- Os restantes métodos (`visitString`, `visitNumber`, etc.): são considerados sempre válidos.

---

## Exemplo de Utilização

```kotlin
// Verificar homogeneidade de um array
val array = JsonArray()
array.add(JsonNumber(1))
array.add(JsonNumber(2))
val homogeneityVisitor = ArrayHomogeneityVisitor()
array.accept(homogeneityVisitor)
val ehHomogeneo = homogeneityVisitor.isHomogeneous()

// Validar uma estrutura JSON
val validator = ValidatorVisitor()
jsonValue.accept(validator)
val ehValido = validator.isValid()