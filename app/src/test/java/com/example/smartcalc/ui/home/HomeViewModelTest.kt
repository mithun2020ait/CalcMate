package com.example.smartcalc.ui.home

import com.example.smartcalc.model.CalculatorType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun init_loadsAllSixCalculatorCards() {
        val uiState = viewModel.uiState.value
        assertEquals(6, uiState.calculators.size)
        assertEquals(CalculatorType.BASIC, uiState.calculators[0].type)
        assertEquals(CalculatorType.GST, uiState.calculators[1].type)
        assertEquals(CalculatorType.DISCOUNT, uiState.calculators[2].type)
        assertEquals(CalculatorType.PROFIT_LOSS, uiState.calculators[3].type)
        assertEquals(CalculatorType.PERCENTAGE, uiState.calculators[4].type)
        assertEquals(CalculatorType.HISTORY, uiState.calculators[5].type)
    }

    @Test
    fun onCalculatorSelected_updatesSelectedCalculatorAndUserMessage() {
        viewModel.onCalculatorSelected(CalculatorType.GST)

        val uiState = viewModel.uiState.value
        assertEquals(CalculatorType.GST, uiState.selectedCalculator)
        assertEquals("Selected GST calculator", uiState.userMessage)
    }

    @Test
    fun onSettingsClicked_updatesUserMessage() {
        viewModel.onSettingsClicked()

        val uiState = viewModel.uiState.value
        assertEquals("Settings clicked", uiState.userMessage)
    }

    @Test
    fun onUserMessageShown_clearsUserMessage() {
        viewModel.onSettingsClicked()
        viewModel.onUserMessageShown()

        val uiState = viewModel.uiState.value
        assertNull(uiState.userMessage)
    }
}
