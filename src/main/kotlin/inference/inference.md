# JsonInfer Package Documentation

## Overview
The JsonInfer package provides functionality to automatically convert Kotlin objects into their JSON representations using reflection.

## Classes

### JsonInfer (object)
A singleton object that handles the conversion of Kotlin objects to JSON values.

#### Public Methods
- `fun infer(value: Any?): JsonValue`
  - Main entry point for JSON inference
  - Converts any Kotlin value to its corresponding JSON representation
  - Parameters:
    - `value`: Any nullable Kotlin object
  - Returns: Appropriate JsonValue subtype

#### Private Methods
- `private fun inferList(list: List<*>): JsonArray`
  - Converts Kotlin lists to JsonArray
  - Recursively processes each element
  - Returns: JsonArray containing converted elements

- `private fun inferMap(map: Map<*, *>): JsonObject`
  - Converts Kotlin maps to JsonObject
  - Only processes string keys
  - Recursively converts values
  - Returns: JsonObject with converted key-value pairs

- `private fun inferDataClass(obj: Any): JsonObject`
  - Converts Kotlin data classes to JsonObject
  - Uses reflection to access properties
  - Creates JSON representation of class properties
  - Returns: JsonObject containing all properties

## Type Mapping
| Kotlin Type | JSON Type |
|-------------|-----------|
| `null` | JsonNull |
| `String` | JsonString |
| `Number` | JsonNumber |
| `Boolean` | JsonBoolean |
| `Enum` | JsonString (enum name) |
| `List<*>` | JsonArray |
| `Map<*, *>` | JsonObject |
| Data Class | JsonObject |

## Usage Example
```kotlin
// Basic types
val jsonNumber = JsonInfer.infer(42)
val jsonString = JsonInfer.infer("hello")

// Complex types
data class Person(val name: String, val age: Int)
val person = Person("John", 30)
val jsonPerson = JsonInfer.infer(person)