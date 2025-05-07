# GetJson Package Documentation

## Overview
The GetJson package provides a lightweight HTTP server implementation for JSON-based REST APIs, with support for routing, parameter handling, and automatic JSON serialization.

## Classes

### GetJson
Main class responsible for setting up and managing the HTTP server.

#### Constructor
- `GetJson(vararg controllerClasses: KClass<*>)`
  - Initializes the server with controller classes
  - Scans for `@Mapping` annotations to set up routes

#### Public Methods
- `fun start(port: Int): HttpServer`
  - Starts the server on specified port
  - Returns the HttpServer instance
  - Prints confirmation message with port number

#### Private Methods
- `private fun handle(exchange: HttpExchange)`
  - Handles incoming HTTP requests
  - Matches routes and executes corresponding controller methods
  - Serializes responses to JSON

- `private fun match(routePattern: String, path: String): Boolean`
  - Matches URL paths against route patterns
  - Supports path variables in {variable} format

- `private fun buildArgs(exchange: HttpExchange, route: Route): List<Any?>`
  - Extracts and converts path variables and query parameters
  - Maps parameters to controller method arguments

- `private fun convert(raw: String?, target: KClass<*>): Any?`
  - Converts string parameters to appropriate types
  - Supports: String, Int, Double, Boolean

#### Inner Classes
- `private data class Route`
  - Properties:
    - `rawPath: String`: The route path pattern
    - `instance: Any`: Controller instance
    - `method: KFunction<*>`: Controller method

## Annotations

### @Mapping
- Target: Classes and Functions
- Purpose: Defines URL mappings for controllers and methods
- Parameters:
  - `value: String`: The URL path

### @Path
- Target: Method Parameters
- Purpose: Marks parameters to be bound from path variables
- No additional parameters

### @Param
- Target: Method Parameters
- Purpose: Marks parameters to be bound from query parameters
- Parameters:
  - `value: String`: Custom parameter name (optional)

## Usage Example
```kotlin
@Mapping("/api")
class UserController {
    @Mapping("/user/{id}")
    fun getUser(@Path id: Int, @Param name: String?): User {
        // Handler implementation
    }
}

val server = GetJson(UserController::class).start(8080)