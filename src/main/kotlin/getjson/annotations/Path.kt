package getjson.annotations

/**
 * Anotação usada para associar um parâmetro ao segmento dinâmico de uma rota.
 *
 * Exemplo:
 * ```
 * @Mapping("eco/{palavra}")
 * fun eco(@Path palavra: String): String = palavra.uppercase()
 * ```
 * A chamada: `/eco/ola` → retorna `"OLA"`
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path