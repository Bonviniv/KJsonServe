package model

// Representa um valor JSON abstrato (base para todos os tipos JSON)
abstract class JsonValue {
    // Converte o valor JSON para sua representação em string
    abstract fun serialize(): String
}
