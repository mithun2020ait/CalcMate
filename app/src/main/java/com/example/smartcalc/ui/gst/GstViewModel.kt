package com.example.smartcalc.ui.gst

import androidx.lifecycle.ViewModel
import com.example.smartcalc.domain.calculator.GstCalculationMode
import com.example.smartcalc.domain.calculator.GstCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel managing UI state and GST calculation requests.
 */
class GstViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GstUiState())
    val uiState: StateFlow<GstUiState> = _uiState.asStateFlow()

    val presetRates = listOf("0", "5", "12", "18", "28")

    init {
        recalculate()
    }

    fun onAmountChange(newAmount: String) {
        if (newAmount.length > GstCalculator.MAX_INPUT_LENGTH) return
        val filtered = newAmount.filter { it.isDigit() || it == '.' }
        if (filtered.count { it == '.' } > 1) return

        _uiState.update { currentState ->
            currentState.copy(amountInput = filtered)
        }
        recalculate()
    }

    fun onPresetRateSelected(rate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRate = rate,
                customRateInput = rate,
                isCustomRate = false
            )
        }
        recalculate()
    }

    fun onCustomRateChange(newRate: String) {
        if (newRate.length > 5) return
        val filtered = newRate.filter { it.isDigit() || it == '.' }
        if (filtered.count { it == '.' } > 1) return

        val matchesPreset = presetRates.contains(filtered)

        _uiState.update { currentState ->
            currentState.copy(
                selectedRate = filtered,
                customRateInput = filtered,
                isCustomRate = !matchesPreset
            )
        }
        recalculate()
    }

    fun onModeChange(newMode: GstCalculationMode) {
        _uiState.update { currentState ->
            currentState.copy(mode = newMode)
        }
        recalculate()
    }

    private fun recalculate() {
        val currentState = _uiState.value
        try {
            val calcResult = GstCalculator.calculate(
                amountInput = currentState.amountInput,
                rateInput = currentState.selectedRate,
                mode = currentState.mode
            )
            _uiState.update { state ->
                state.copy(
                    result = calcResult,
                    isError = false,
                    errorMessage = null
                )
            }
        } catch (e: IllegalArgumentException) {
            _uiState.update { state ->
                state.copy(
                    result = null,
                    isError = true,
                    errorMessage = e.message
                )
            }
        } catch (e: Exception) {
            _uiState.update { state ->
                state.copy(
                    result = null,
                    isError = true,
                    errorMessage = "Invalid input"
                )
            }
        }
    }
}
