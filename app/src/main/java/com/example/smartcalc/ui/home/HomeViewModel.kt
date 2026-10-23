package com.example.smartcalc.ui.home

import androidx.lifecycle.ViewModel
import com.example.smartcalc.model.CalculatorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Home Screen managing calculator card selections and state.
 */
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCalculatorCards()
    }

    private fun loadCalculatorCards() {
        val items = CalculatorType.entries.map { type ->
            CalculatorCardItem(
                type = type,
                titleResId = type.titleResId,
                descriptionResId = type.descriptionResId,
                icon = type.icon
            )
        }
        _uiState.update { currentState ->
            currentState.copy(calculators = items)
        }
    }

    fun onCalculatorSelected(type: CalculatorType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCalculator = type,
                userMessage = "Selected ${type.name} calculator"
            )
        }
    }

    fun onSettingsClicked() {
        _uiState.update { currentState ->
            currentState.copy(
                userMessage = "Settings clicked"
            )
        }
    }

    fun onUserMessageShown() {
        _uiState.update { currentState ->
            currentState.copy(userMessage = null)
        }
    }
}
