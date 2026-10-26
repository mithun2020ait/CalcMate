package com.example.smartcalc.ui.calculator

import com.example.smartcalc.model.CalculationResult

/**
 * Immutable UI State for the Basic Calculator screen.
 */
data class BasicCalculatorUiState(
    val expression: String = "",
    val resultPreview: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val recentCalculations: List<CalculationResult> = emptyList()
)
