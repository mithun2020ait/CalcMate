package com.example.smartcalc.ui.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BasicCalculatorViewModelTest {

    private lateinit var viewModel: BasicCalculatorViewModel

    @Before
    fun setUp() {
        viewModel = BasicCalculatorViewModel()
    }

    @Test
    fun onDigitClick_appendsDigitsAndUpdatesPreview() {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("0")
        viewModel.onDigitClick("0")

        val state = viewModel.uiState.value
        assertEquals("100", state.expression)
        assertFalse(state.isError)
    }

    @Test
    fun onOperatorClick_replacesConsecutiveOperators() {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("0")
        viewModel.onDigitClick("0")
        viewModel.onOperatorClick("+")
        viewModel.onOperatorClick("×")

        val state = viewModel.uiState.value
        assertEquals("100×", state.expression)
    }

    @Test
    fun onDecimalClick_preventsDuplicateDecimals() {
        viewModel.onDigitClick("1")
        viewModel.onDecimalClick()
        viewModel.onDigitClick("5")
        viewModel.onDecimalClick() // Second decimal ignored

        val state = viewModel.uiState.value
        assertEquals("1.5", state.expression)
    }

    @Test
    fun onClearClick_resetsState() {
        viewModel.onDigitClick("5")
        viewModel.onOperatorClick("+")
        viewModel.onDigitClick("5")
        viewModel.onClearClick()

        val state = viewModel.uiState.value
        assertEquals("", state.expression)
        assertEquals("", state.resultPreview)
        assertFalse(state.isError)
    }

    @Test
    fun onBackspaceClick_removesLastCharacter() {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("2")
        viewModel.onBackspaceClick()

        val state = viewModel.uiState.value
        assertEquals("1", state.expression)
    }

    @Test
    fun onEqualsClick_calculatesCorrectResult() {
        viewModel.onDigitClick("2")
        viewModel.onOperatorClick("+")
        viewModel.onDigitClick("3")
        viewModel.onOperatorClick("×")
        viewModel.onDigitClick("4")
        viewModel.onEqualsClick()

        val state = viewModel.uiState.value
        assertEquals("14", state.expression)
        assertFalse(state.isError)
    }

    @Test
    fun divisionByZero_setsErrorState() {
        viewModel.onDigitClick("5")
        viewModel.onOperatorClick("÷")
        viewModel.onDigitClick("0")
        viewModel.onEqualsClick()

        val state = viewModel.uiState.value
        assertTrue(state.isError)
        assertEquals("Cannot divide by zero", state.errorMessage)
    }
}
