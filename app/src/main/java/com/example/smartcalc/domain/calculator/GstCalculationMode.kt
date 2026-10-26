package com.example.smartcalc.domain.calculator

/**
 * Modes of GST calculation:
 * EXCLUSIVE - GST is added on top of the base amount.
 * INCLUSIVE - GST is already included inside the final total amount.
 */
enum class GstCalculationMode {
    EXCLUSIVE,
    INCLUSIVE
}
