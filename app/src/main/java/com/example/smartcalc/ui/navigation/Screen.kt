package com.example.smartcalc.ui.navigation

/**
 * Type-safe screen route definitions for Navigation Compose.
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object BasicCalculator : Screen("basic_calculator")
    data object GstCalculator : Screen("gst_calculator")
    data object DiscountCalculator : Screen("discount_calculator")
    data object ProfitCalculator : Screen("profit_calculator")
    data object PercentageCalculator : Screen("percentage_calculator")
    data object History : Screen("history")
    data object Settings : Screen("settings")
}
