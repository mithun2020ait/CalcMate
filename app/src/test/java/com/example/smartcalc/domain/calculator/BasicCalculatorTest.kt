package com.example.smartcalc.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class BasicCalculatorTest {

    @Test
    fun addition_isCorrect() {
        val result = BasicCalculator.evaluate("100 + 25")
        assertEquals(0, BigDecimal("125").compareTo(result))
    }

    @Test
    fun subtraction_isCorrect() {
        val result = BasicCalculator.evaluate("100 − 25")
        assertEquals(0, BigDecimal("75").compareTo(result))
    }

    @Test
    fun multiplication_isCorrect() {
        val result = BasicCalculator.evaluate("100 × 20")
        assertEquals(0, BigDecimal("2000").compareTo(result))
    }

    @Test
    fun division_isCorrect() {
        val result = BasicCalculator.evaluate("500 ÷ 5")
        assertEquals(0, BigDecimal("100").compareTo(result))
    }

    @Test
    fun operatorPrecedence_isCorrect() {
        val result = BasicCalculator.evaluate("2 + 3 × 4")
        assertEquals(0, BigDecimal("14").compareTo(result))
    }

    @Test
    fun decimalCalculations_isCorrect() {
        val result = BasicCalculator.evaluate("12.5 + 2.5")
        assertEquals(0, BigDecimal("15").compareTo(result))
    }

    @Test
    fun negativeNumbers_isCorrect() {
        val result = BasicCalculator.evaluate("(-25) + 100")
        assertEquals(0, BigDecimal("75").compareTo(result))
    }

    @Test
    fun multiplicativePercentage_isCorrect() {
        val result = BasicCalculator.evaluate("200 × 10%")
        assertEquals(0, BigDecimal("20").compareTo(result))
    }

    @Test
    fun additivePercentage_isCorrect() {
        val result = BasicCalculator.evaluate("100 + 20%")
        assertEquals(0, BigDecimal("120").compareTo(result))
    }

    @Test
    fun subtractivePercentage_isCorrect() {
        val result = BasicCalculator.evaluate("100 − 20%")
        assertEquals(0, BigDecimal("80").compareTo(result))
    }

    @Test(expected = ArithmeticException::class)
    fun divisionByZero_throwsException() {
        BasicCalculator.evaluate("5 ÷ 0")
    }

    @Test
    fun emptyExpression_returnsZero() {
        val result = BasicCalculator.evaluate("")
        assertEquals(0, BigDecimal.ZERO.compareTo(result))
    }

    @Test
    fun indianNumberFormatting_isCorrect() {
        assertEquals("1,000", BasicCalculator.formatIndianNumberString("1000"))
        assertEquals("1,00,000", BasicCalculator.formatIndianNumberString("100000"))
        assertEquals("1,00,00,000", BasicCalculator.formatIndianNumberString("10000000"))
        assertEquals("125.5", BasicCalculator.formatIndianNumberString("125.5"))
    }
}
