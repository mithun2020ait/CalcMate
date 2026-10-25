package com.example.smartcalc.ui.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartcalc.model.CalculatorType

/**
 * UI representation of a calculator card item.
 */
data class CalculatorCardItem(
    val type: CalculatorType,
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector,
    val iconColor: Color
)

/**
 * Immutable UI State for the Home Screen dashboard.
 */
data class HomeUiState(
    val calculators: List<CalculatorCardItem> = emptyList(),
    val selectedCalculator: CalculatorType? = null,
    val userMessage: String? = null
)
