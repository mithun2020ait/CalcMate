package com.example.smartcalc.domain.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * Pure Kotlin GST calculation engine handling GST Exclusive and Inclusive formulas,
 * monetary rounding policies (HALF_UP to 2 decimal places), validation, and Indian currency formatting.
 */
object GstCalculator {

    private val MATH_CONTEXT = MathContext(16, RoundingMode.HALF_UP)
    private val HUNDRED = BigDecimal("100")
    const val MAX_INPUT_LENGTH = 15
    val MAX_AMOUNT = BigDecimal("100000000000") // 10,000 Crore limit

    /**
     * Calculates GST according to the specified mode and inputs.
     * Rounding Policy:
     * - Money fields rounded to 2 decimal places using RoundingMode.HALF_UP.
     * - Guaranteed reconciliation: Rounded Base + Rounded GST = Rounded Total.
     */
    fun calculate(
        amountInput: String,
        rateInput: String,
        mode: GstCalculationMode
    ): GstCalculationResult {
        val (validAmount, validRate) = validateAndParseInputs(amountInput, rateInput)

        val rateRatio = validRate.divide(HUNDRED, MATH_CONTEXT)

        val baseAmount: BigDecimal
        val gstAmount: BigDecimal
        val totalAmount: BigDecimal

        when (mode) {
            GstCalculationMode.EXCLUSIVE -> {
                // Exclusive mode: Amount entered is Base Amount
                baseAmount = validAmount.setScale(2, RoundingMode.HALF_UP)
                gstAmount = baseAmount.multiply(rateRatio, MATH_CONTEXT).setScale(2, RoundingMode.HALF_UP)
                totalAmount = baseAmount.add(gstAmount)
            }
            GstCalculationMode.INCLUSIVE -> {
                // Inclusive mode: Amount entered is Total Amount
                totalAmount = validAmount.setScale(2, RoundingMode.HALF_UP)
                val divisor = BigDecimal.ONE.add(rateRatio, MATH_CONTEXT)
                baseAmount = totalAmount.divide(divisor, 2, RoundingMode.HALF_UP)
                gstAmount = totalAmount.subtract(baseAmount)
            }
        }

        return GstCalculationResult(
            mode = mode,
            gstRate = validRate,
            rawAmountInput = amountInput.trim(),
            baseAmount = baseAmount,
            gstAmount = gstAmount,
            totalAmount = totalAmount,
            formattedBaseAmount = formatIndianCurrency(baseAmount),
            formattedGstAmount = formatIndianCurrency(gstAmount),
            formattedTotalAmount = formatIndianCurrency(totalAmount)
        )
    }

    /**
     * Validates input strings and returns parsed BigDecimal values.
     * Throws IllegalArgumentException with friendly error message on invalid input.
     */
    fun validateAndParseInputs(
        amountInput: String,
        rateInput: String
    ): Pair<BigDecimal, BigDecimal> {
        val cleanAmount = amountInput.trim()
        val cleanRate = rateInput.trim()

        if (cleanAmount.isEmpty()) {
            throw IllegalArgumentException("Please enter an amount")
        }
        if (cleanAmount.length > MAX_INPUT_LENGTH) {
            throw IllegalArgumentException("Amount is too large")
        }

        val amount = try {
            BigDecimal(cleanAmount)
        } catch (e: Exception) {
            throw IllegalArgumentException("Enter a valid amount")
        }

        if (amount < BigDecimal.ZERO) {
            throw IllegalArgumentException("Amount cannot be negative")
        }
        if (amount > MAX_AMOUNT) {
            throw IllegalArgumentException("Amount is too large")
        }

        if (cleanRate.isEmpty()) {
            throw IllegalArgumentException("Please enter a GST rate")
        }

        val rate = try {
            BigDecimal(cleanRate)
        } catch (e: Exception) {
            throw IllegalArgumentException("GST rate must be between 0 and 100")
        }

        if (rate < BigDecimal.ZERO || rate > HUNDRED) {
            throw IllegalArgumentException("GST rate must be between 0 and 100")
        }

        return Pair(amount, rate)
    }

    /**
     * Formats a BigDecimal into Indian Rupees format.
     * Examples:
     * 1000 -> "₹1,000.00"
     * 100000 -> "₹1,00,000.00"
     */
    fun formatIndianCurrency(amount: BigDecimal): String {
        val scaled = amount.setScale(2, RoundingMode.HALF_UP)
        val plainString = scaled.toPlainString()
        val isNegative = plainString.startsWith("-")
        val clean = if (isNegative) plainString.substring(1) else plainString

        val parts = clean.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else "00"

        val formattedInteger = if (integerPart.length <= 3) {
            integerPart
        } else {
            val lastThree = integerPart.takeLast(3)
            val rest = integerPart.dropLast(3)
            val formattedRest = rest.reversed().chunked(2).joinToString(",").reversed()
            "$formattedRest,$lastThree"
        }

        val prefix = if (isNegative) "-₹" else "₹"
        return "$prefix$formattedInteger.$decimalPart"
    }
}
