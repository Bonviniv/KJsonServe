# Test Package Documentation

## Overview
The test package contains comprehensive test suites for validating JSON functionality, including value handling, serialization, inference, and HTTP endpoints.

## Test Classes

### TestsPhase1
Tests basic JSON functionality and operations.

#### Test Methods
- `testBasicValues()`
  - Tests serialization of basic JSON types (String, Number, Boolean, Null)
- `testArrayOperations()`
  - Validates JSON array manipulation and serialization
- `testObjectOperations()`
  - Tests JSON object property management
- `testComplexStructure()`
  - Verifies nested JSON structures
- `testObjectFilter()`
  - Tests object property filtering
- `testArrayFilter()`
  - Validates array element filtering
- `testArrayMap()`
  - Tests array transformation operations
- `testValidatorVisitor()`
  - Validates JSON structure using visitor pattern

### TestsPhase2
Tests JSON inference functionality.

#### Test Methods
- `testInferBasicTypes()`
  - Tests inference of primitive types
- `testInferEnum()`
  - Validates enum value inference
- `testInferList()`
  - Tests list to JSON array conversion
- `testInferMap()`
  - Validates map to JSON object conversion
- `testInferSimpleDataClass()`
  - Tests basic data class inference
- `testInferComplexDataClass()`
  - Validates nested data structure inference
- `testInferWithNulls()`
  - Tests null value handling in inference

### TestsPhase3
Tests HTTP endpoint functionality.

#### Properties
- `server: HttpServer?`
- `port: Int`

#### Setup/Teardown
- `setup()`
  - Initializes test environment
- `tearDown()`
  - Cleans up server resources

#### Test Methods
- `testBasicEndpoints()`
  - Tests basic HTTP endpoints
- `testPathVariables()`
  - Validates path parameter handling
- `testQueryParameters()`
  - Tests query parameter processing
- `testComplexResponse()`
  - Validates complex JSON responses
- `testInvalidPath()`
  - Tests error handling for invalid paths
- `testInvalidParameters()`
  - Validates parameter validation

### JsonBooleanTests
Tests JSON boolean value handling.

#### Test Methods
- `testBooleanSerialization()`
  - Validates boolean serialization

### JsonNullTests
Tests JSON null value handling.

#### Test Methods
- `testNullSerialization()`
  - Validates null value serialization

### JsonObjectTest
Tests JSON object operations.

#### Test Methods
- `testCreateEmptyObject()`
  - Tests empty object creation
- `testPutAndGetValue()`
  - Validates property management
- `testSize()`
  - Tests object size tracking
- `testSerialize()`
  - Validates object serialization
- `testAccept()`
  - Tests visitor pattern implementation
- `testJsonObjectFilter()`
  - Validates object filtering

### JsonArrayTest
Tests JSON array operations.

#### Test Methods
- `testCreateEmptyArray()`
  - Tests empty array creation
- `testAddAndGetElement()`
  - Validates element management
- `testSetElement()`
  - Tests element modification
- `testSize()`
  - Validates array size tracking
- `testFilter()`
  - Tests array filtering
- `testMap()`
  - Validates array transformation
- `testSerialize()`
  - Tests array serialization
- `testAccept()`
  - Validates visitor pattern implementation

### JsonInferTests
Tests JSON inference functionality.

#### Test Methods
- `testInferNull()`
  - Tests null inference
- `testInferExistingJsonValue()`
  - Validates existing JSON value handling
- `testInferPrimitiveTypes()`
  - Tests primitive type inference
- `testInferEnum()`
  - Validates enum inference
- `testInferList()`
  - Tests list inference
- `testInferMap()`
  - Validates map inference
- `testInferDataClassSimple()`
  - Tests simple data class inference
- `testInferDataClassNested()`
  - Validates nested data class inference
- `testInferExampleCourse()`
  - Tests complex course structure inference

### ValidatorVisitorTests
Tests JSON structure validation.

#### Test Methods
- `testValidObjectNoEmptyKeys()`
  - Tests valid object validation
- `testObjectWithEmptyKeyIsInvalid()`
  - Validates empty key detection
- `testNestedObjectsTraversal()`
  - Tests nested structure validation