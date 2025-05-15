package getjson.annotations

/**
 * Anotação usada para mapear controladores e métodos para URLs.
 *
 * Pode ser usada a dois níveis:
 * - Em cima de uma classe: define o prefixo do caminho (por ex., "api").
 * - Em cima de um método: define o sufixo do caminho (por ex., "user").
 *
 * Exemplo:
 * ```
 * @Mapping("api")
 * class MeuController {
 *     @Mapping("hello")
 *     fun dizOla(): String = "Olá"
 * }
 * ```
 * Resultado: `/api/hello`
 *
 * @property value Caminho associado à rota.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mapping(val value: String)