package com.example.smartcalc.domain.calculator

import com.example.smartcalc.model.CalculatorType
import java.math.BigDecimal
import java.util.UUID

/**
 * Domain model representing a completed GST calculation result.
 * Structured for future Room database persistence.
 */
data class GstCalculationResult(
    val id: String = UUID.randomUUID().toString(),
    val mode: GstCalculationMode,
    val gstRate: BigDecimal,
    val rawAmountInput: String,
    val baseAmount: BigDecimal,
    val gstAmount: BigDecimal,
    val totalAmount: BigDecimal,
    val formattedBaseAmount: String,
    val formattedGstAmount: String,
    val formattedTotalAmount: String,
    val timestamp: Long = System.currentTimeMillis(),
    val calculatorType: CalculatorType = CalculatorType.GST
)
