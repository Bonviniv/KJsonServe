package getjson

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

/**
 * Suite de testes JUnit para o módulo GetJson.
 *
 * Esta classe agrupa os testes relacionados com a framework REST
 * para que possam ser executados em conjunto.
 */
@RunWith(Suite::class)
@SuiteClasses(
    GetJsonTests::class // Adiciona a classe de testes principal
)
class GetJsonTestSuite