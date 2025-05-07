# JSON Model Package Documentation

## Overview
The model package provides the core JSON value types and their implementations, following the JSON specification.

## Base Class

### JsonValue (abstract)
Base class for all JSON value types.

#### Abstract Methods
- `fun serialize(): String`
  - Converts JSON value to its string representation
- `fun accept(visitor: JsonVisitor)`
  - Implements Visitor pattern for JSON operations

## Concrete Classes

### JsonObject
Represents a JSON object as key-value pairs.

#### Properties
- `properties: MutableMap<String, JsonValue>`

#### Methods
- `fun get(key: String): JsonValue?`
  - Retrieves value by key
- `fun set(key: String, value: JsonValue)`
  - Sets value for key
- `fun size(): Int`
  - Returns number of properties
- `fun values(): Collection<JsonValue>`
  - Returns all values
- `fun keys(): Set<String>`
  - Returns all keys
- `fun getKeys(): Set<String>`
  - Alternative method for getting keys
- `operator fun times(other: JsonObject): JsonObject`
  - Intersection of two objects
- `fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject`
  - Filters properties based on predicate

### JsonArray
Represents a JSON array.

#### Properties
- `elements: MutableList<JsonValue>`

#### Methods
- `fun add(value: JsonValue)`
  - Adds element to array
- `fun get(index: Int): JsonValue`
  - Gets element at index
- `fun set(index: Int, value: JsonValue)`
  - Sets element at index
- `fun size(): Int`
  - Returns array size
- `fun elements(): List<JsonValue>`
  - Returns all elements
- `fun filter(predicate: (JsonValue) -> Boolean): JsonArray`
  - Filters elements
- `fun map(transform: (JsonValue) -> JsonValue): JsonArray`
  - Transforms elements

### JsonString
Represents a JSON string value.

#### Properties
- `value: String`

#### Methods
- `override fun serialize(): String`
  - Handles special character escaping
- `override fun equals(other: Any?): Boolean`
  - String value comparison
- `override fun hashCode(): Int`
  - Consistent with equals

### JsonNumber
Represents a JSON numeric value.

#### Properties
- `value: Number`

#### Methods
- `override fun serialize(): String`
  - Formats integers and decimals appropriately

### JsonBoolean
Represents a JSON boolean value.

#### Properties
- `value: Boolean`

#### Methods
- `override fun serialize(): String`
  - Returns "true" or "false"

### JsonNull
Represents a JSON null value.

#### Properties
- Singleton object

#### Methods
- `override fun serialize(): String`
  - Returns "null"

## Common Features
All classes implement:
- Serialization to valid JSON format
- Visitor pattern support
- Type-safe operations
- Immutable value semantics

## Usage Example
```kotlin
// Creating a JSON object
val person = JsonObject().apply {
    set("name", JsonString("John"))
    set("age", JsonNumber(30))
    set("address", JsonObject().apply {
        set("city", JsonString("New York"))
    })
}

// Serializing to JSON string
val json = person.serialize()