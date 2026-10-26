package com.example.smartcalc.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class GstCalculatorTest {

    @Test
    fun exclusiveGst_at18Percent_isCorrect() {
        val res = GstCalculator.calculate("1000", "18", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("1000.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("180.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("1180.00").compareTo(res.totalAmount))
        assertEquals("₹1,000.00", res.formattedBaseAmount)
        assertEquals("₹180.00", res.formattedGstAmount)
        assertEquals("₹1,180.00", res.formattedTotalAmount)
    }

    @Test
    fun inclusiveGst_at18Percent_isCorrect() {
        val res = GstCalculator.calculate("1180", "18", GstCalculationMode.INCLUSIVE)
        assertEquals(0, BigDecimal("1000.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("180.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("1180.00").compareTo(res.totalAmount))
        assertEquals("₹1,000.00", res.formattedBaseAmount)
        assertEquals("₹180.00", res.formattedGstAmount)
        assertEquals("₹1,180.00", res.formattedTotalAmount)
    }

    @Test
    fun zeroGst_exclusiveAndInclusive_isCorrect() {
        val resEx = GstCalculator.calculate("1000", "0", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("1000.00").compareTo(resEx.baseAmount))
        assertEquals(0, BigDecimal("0.00").compareTo(resEx.gstAmount))
        assertEquals(0, BigDecimal("1000.00").compareTo(resEx.totalAmount))

        val resInc = GstCalculator.calculate("1000", "0", GstCalculationMode.INCLUSIVE)
        assertEquals(0, BigDecimal("1000.00").compareTo(resInc.baseAmount))
        assertEquals(0, BigDecimal("0.00").compareTo(resInc.gstAmount))
        assertEquals(0, BigDecimal("1000.00").compareTo(resInc.totalAmount))
    }

    @Test
    fun gstAt5Percent_isCorrect() {
        val res = GstCalculator.calculate("100", "5", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("100.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("5.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("105.00").compareTo(res.totalAmount))
    }

    @Test
    fun gstAt12Percent_isCorrect() {
        val res = GstCalculator.calculate("100", "12", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("100.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("12.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("112.00").compareTo(res.totalAmount))
    }

    @Test
    fun gstAt28Percent_isCorrect() {
        val res = GstCalculator.calculate("100", "28", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("100.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("28.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("128.00").compareTo(res.totalAmount))
    }

    @Test
    fun customGstRate_isCorrect() {
        val res = GstCalculator.calculate("1000", "13.5", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("1000.00").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("135.00").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("1135.00").compareTo(res.totalAmount))
    }

    @Test
    fun decimalAmount_isCorrect() {
        val res = GstCalculator.calculate("1234.56", "18", GstCalculationMode.EXCLUSIVE)
        assertEquals(0, BigDecimal("1234.56").compareTo(res.baseAmount))
        assertEquals(0, BigDecimal("222.22").compareTo(res.gstAmount))
        assertEquals(0, BigDecimal("1456.78").compareTo(res.totalAmount))
    }

    @Test
    fun monetaryRounding_reconciliationCheck() {
        val res = GstCalculator.calculate("999.99", "18", GstCalculationMode.EXCLUSIVE)
        assertEquals(res.totalAmount, res.baseAmount.add(res.gstAmount))

        val resInc = GstCalculator.calculate("999.99", "18", GstCalculationMode.INCLUSIVE)
        assertEquals(resInc.totalAmount, resInc.baseAmount.add(resInc.gstAmount))
    }

    @Test(expected = IllegalArgumentException::class)
    fun emptyAmount_throwsException() {
        GstCalculator.calculate("", "18", GstCalculationMode.EXCLUSIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun negativeAmount_throwsException() {
        GstCalculator.calculate("-1000", "18", GstCalculationMode.EXCLUSIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidGstRate_throwsException() {
        GstCalculator.calculate("1000", "-5", GstCalculationMode.EXCLUSIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rateAbove100_throwsException() {
        GstCalculator.calculate("1000", "105", GstCalculationMode.EXCLUSIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidNumericInput_throwsException() {
        GstCalculator.calculate("abc", "18", GstCalculationMode.EXCLUSIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun extremelyLargeInput_throwsException() {
        GstCalculator.calculate("10000000000000000", "18", GstCalculationMode.EXCLUSIVE)
    }

    @Test
    fun indianCurrencyFormatting_isCorrect() {
        assertEquals("₹1,000.00", GstCalculator.formatIndianCurrency(BigDecimal("1000")))
        assertEquals("₹1,00,000.00", GstCalculator.formatIndianCurrency(BigDecimal("100000")))
        assertEquals("₹1,00,00,000.00", GstCalculator.formatIndianCurrency(BigDecimal("10000000")))
    }
}
