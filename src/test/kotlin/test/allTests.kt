package test

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import test.TestsPhase1
import test.TestsPhase2
import test.ValidatorVisitorTests
import test.ArrayHomogeneityVisitorTests
import test.JsonBooleanTests
import test.JsonNullTests
import test.JsonNumberTests
import test.JsonStringTests
import test.JsonInferTests
import test.JsonInstantiationTests

/**
 * Suite de testes JUnit que agrupa todos os testes da biblioteca JSON.
 * Permite executar facilmente todos os testes da Fase 1, Fase 2,
 * dos visitantes e dos tipos JSON básicos.
 */
@RunWith(Suite::class)
@SuiteClasses(
    TestsPhase1::class,
    TestsPhase2::class,
    ValidatorVisitorTests::class,
    ArrayHomogeneityVisitorTests::class,
    JsonBooleanTests::class,
    JsonNullTests::class,
    JsonNumberTests::class,
    JsonStringTests::class,
    JsonInferTests::class,
    JsonInstantiationTests::class
)
class AllTests
