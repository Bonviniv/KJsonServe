# Documentação do Pacote `getjson`

## Visão Geral

O pacote `getjson` fornece uma implementação leve de um servidor HTTP para criação de APIs REST baseadas em JSON. Suporta mapeamento de rotas via anotações, passagem de parâmetros (via path e query string) e serialização automática de respostas JSON a partir de valores Kotlin.

---

## Classe Principal

### `GetJson`
Classe principal responsável por configurar e gerir o servidor HTTP.

#### Construtor
- `GetJson(vararg controllerClasses: KClass<*>)`
  - Inicializa o servidor com as classes de controladores fornecidas.
  - Procura anotações `@Mapping` para configurar as rotas automaticamente.

#### Métodos Públicos

- `fun start(port: Int): HttpServer`
  - Inicia o servidor na porta especificada.
  - Devolve a instância de `HttpServer`.
  - Imprime uma mensagem de confirmação com o número da porta.

---

## Métodos Privados

- `handle(exchange: HttpExchange)`
  - Processa pedidos HTTP recebidos.
  - Faz correspondência com a rota definida.
  - Executa o método do controlador.
  - Serializa a resposta como JSON.

- `match(routePattern: String, path: String): Boolean`
  - Compara a rota esperada com o caminho da requisição.
  - Suporta variáveis entre `{}` (e.g. `{id}`).

- `buildArgs(exchange: HttpExchange, route: Route): List<Any?>`
  - Extrai argumentos do caminho (`@Path`) e da query string (`@Param`).
  - Converte os argumentos para os tipos esperados.
  - Prepara os argumentos para invocar o método do controlador.

- `convert(raw: String?, target: KClass<*>): Any?`
  - Converte valores em texto (strings) para os tipos básicos suportados.
  - Suporta os tipos: `String`, `Int`, `Double`, `Boolean`.

---

## Classe Interna

### `Route`
Classe interna que representa uma rota registada.

#### Propriedades
- `rawPath: String` — Padrão da rota (com ou sem variáveis).
- `instance: Any` — Instância do controlador.
- `method: KFunction<*>` — Referência ao método do controlador.

---

## Anotações

### `@Mapping`
- **Alvo:** Classes e funções.
- **Função:** Define o caminho da URL associado a um controlador ou método.
- **Parâmetro:**
  - `value: String` — Caminho a mapear (ex: `"api"` ou `"utilizadores"`).

### `@Path`
- **Alvo:** Parâmetros de função.
- **Função:** Liga o parâmetro ao valor extraído diretamente do caminho da URL (ex: `/user/{id}`).

### `@Param`
- **Alvo:** Parâmetros de função.
- **Função:** Liga o parâmetro ao valor extraído da query string (ex: `?nome=João`).
- **Parâmetro:**
  - `value: String` — Nome do parâmetro (pode ser omitido, usando o nome do argumento).

---

## Exemplo de Utilização

```kotlin
@Mapping("/api")
class UserController {

    @Mapping("/user/{id}")
    fun getUser(@Path id: Int, @Param name: String?): User {
        // Lógica do controlador
    }
}

val server = GetJson(UserController::class).start(8080)