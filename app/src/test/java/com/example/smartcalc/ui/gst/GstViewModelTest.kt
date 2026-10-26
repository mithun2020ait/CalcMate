package com.example.smartcalc.ui.gst

import com.example.smartcalc.domain.calculator.GstCalculationMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GstViewModelTest {

    private lateinit var viewModel: GstViewModel

    @Before
    fun setUp() {
        viewModel = GstViewModel()
    }

    @Test
    fun initialState_calculatesDefaultGstResult() {
        val state = viewModel.uiState.value
        assertEquals("1000", state.amountInput)
        assertEquals("18", state.selectedRate)
        assertEquals(GstCalculationMode.EXCLUSIVE, state.mode)
        assertFalse(state.isError)
        assertNotNull(state.result)
        assertEquals("₹1,180.00", state.result?.formattedTotalAmount)
    }

    @Test
    fun onAmountChange_recalculatesResult() {
        viewModel.onAmountChange("2000")

        val state = viewModel.uiState.value
        assertEquals("2000", state.amountInput)
        assertFalse(state.isError)
        assertEquals("₹2,360.00", state.result?.formattedTotalAmount)
    }

    @Test
    fun onPresetRateSelected_recalculatesResult() {
        viewModel.onPresetRateSelected("12")

        val state = viewModel.uiState.value
        assertEquals("12", state.selectedRate)
        assertFalse(state.isCustomRate)
        assertEquals("₹1,120.00", state.result?.formattedTotalAmount)
    }

    @Test
    fun onCustomRateChange_recalculatesResult() {
        viewModel.onCustomRateChange("15")

        val state = viewModel.uiState.value
        assertEquals("15", state.selectedRate)
        assertTrue(state.isCustomRate)
        assertEquals("₹1,150.00", state.result?.formattedTotalAmount)
    }

    @Test
    fun onModeChange_preservesAmountAndRecalculates() {
        viewModel.onAmountChange("1180")
        viewModel.onModeChange(GstCalculationMode.INCLUSIVE)

        val state = viewModel.uiState.value
        assertEquals(GstCalculationMode.INCLUSIVE, state.mode)
        assertEquals("₹1,000.00", state.result?.formattedBaseAmount)
        assertEquals("₹180.00", state.result?.formattedGstAmount)
        assertEquals("₹1,180.00", state.result?.formattedTotalAmount)
    }

    @Test
    fun invalidInput_setsErrorState() {
        viewModel.onAmountChange("")

        val state = viewModel.uiState.value
        assertTrue(state.isError)
        assertNull(state.result)
        assertEquals("Please enter an amount", state.errorMessage)
    }

    @Test
    fun rateAbove100_setsErrorState() {
        viewModel.onCustomRateChange("150")

        val state = viewModel.uiState.value
        assertTrue(state.isError)
        assertNull(state.result)
        assertEquals("GST rate must be between 0 and 100", state.errorMessage)
    }

    @Test
    fun fixingInvalidInput_clearsError() {
        viewModel.onAmountChange("")
        assertTrue(viewModel.uiState.value.isError)

        viewModel.onAmountChange("500")
        val state = viewModel.uiState.value
        assertFalse(state.isError)
        assertNotNull(state.result)
        assertEquals("₹590.00", state.result?.formattedTotalAmount)
    }
}
