package getjson.annotations

/**
 * Anotação usada para indicar que um parâmetro de função
 * deve ser lido da query string de uma URL.
 *
 * Exemplo:
 * ```
 * @Mapping("exemplo")
 * fun saudacao(@Param nome: String): String = "Olá, $nome"
 * ```
 * A chamada: `/exemplo?nome=João` → retorna `"Olá, João"`
 *
 * @property value Nome do parâmetro (pode ser omitido para usar o nome da variável).
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Param(val value: String = "")