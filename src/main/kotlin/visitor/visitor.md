# JSON Visitor Package Documentation

## Overview
The visitor package implements the Visitor pattern for JSON structure traversal and validation operations.

## Interface

### JsonVisitor
Base interface for all JSON visitors.

#### Methods
- `fun visitObject(jsonObject: JsonObject)`
  - Processes JSON object nodes
- `fun visitArray(jsonArray: JsonArray)`
  - Processes JSON array nodes
- `fun visitString(jsonString: JsonString)`
  - Processes JSON string values
- `fun visitNumber(jsonNumber: JsonNumber)`
  - Processes JSON numeric values
- `fun visitBoolean(jsonBoolean: JsonBoolean)`
  - Processes JSON boolean values
- `fun visitNull(jsonNull: JsonNull)`
  - Processes JSON null values

## Concrete Visitors

### ArrayHomogeneityVisitor
Verifies that arrays contain elements of the same type.

#### Properties
- `private var currentType: Class<*>?`
  - Tracks the current element type
- `private var isHomogeneous: Boolean`
  - Stores homogeneity state

#### Methods
- `fun isHomogeneous(): Boolean`
  - Returns array homogeneity status
- `private fun checkType(type: Class<*>)`
  - Validates type consistency

#### Visit Implementations
- `visitObject`: Ignores objects (don't affect homogeneity)
- `visitArray`: Recursively checks nested arrays
- `visitString`, `visitNumber`, `visitBoolean`, `visitNull`: Check type consistency

### ValidatorVisitor
Validates JSON structure integrity.

#### Properties
- `private var isValid: Boolean`
  - Tracks validation state
- `private val visitedObjects: MutableSet<JsonObject>`
  - Detects circular references

#### Methods
- `fun isValid(): Boolean`
  - Returns validation status

#### Visit Implementations
- `visitObject`:
  - Checks for circular references
  - Validates key names (non-empty)
  - Traverses object properties
- `visitArray`: Traverses array elements
- Other visit methods: No additional validation needed

## Usage Example
```kotlin
// Checking array homogeneity
val array = JsonArray()
array.add(JsonNumber(1))
array.add(JsonNumber(2))
val homogeneityVisitor = ArrayHomogeneityVisitor()
array.accept(homogeneityVisitor)
val isHomogeneous = homogeneityVisitor.isHomogeneous()

// Validating JSON structure
val validator = ValidatorVisitor()
jsonValue.accept(validator)
val isValid = validator.isValid()