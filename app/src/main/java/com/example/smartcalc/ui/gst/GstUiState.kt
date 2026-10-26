package com.example.smartcalc.ui.gst

import com.example.smartcalc.domain.calculator.GstCalculationMode
import com.example.smartcalc.domain.calculator.GstCalculationResult

/**
 * Immutable UI State for the GST Calculator screen.
 */
data class GstUiState(
    val amountInput: String = "1000",
    val selectedRate: String = "18",
    val customRateInput: String = "18",
    val isCustomRate: Boolean = false,
    val mode: GstCalculationMode = GstCalculationMode.EXCLUSIVE,
    val result: GstCalculationResult? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
