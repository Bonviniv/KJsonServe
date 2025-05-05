package test

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

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