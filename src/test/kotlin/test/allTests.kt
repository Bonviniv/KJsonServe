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