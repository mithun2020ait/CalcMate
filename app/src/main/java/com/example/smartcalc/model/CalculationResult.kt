package com.example.smartcalc.model

import java.util.UUID

/**
 * Data model representing a completed calculation result.
 * Designed for future Room database persistence.
 */
data class CalculationResult(
    val id: String = UUID.randomUUID().toString(),
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis(),
    val calculatorType: CalculatorType = CalculatorType.BASIC
)
