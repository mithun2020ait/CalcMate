package com.example.smartcalc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcalc.model.CalculatorType
import com.example.smartcalc.ui.calculator.BasicCalculatorScreen
import com.example.smartcalc.ui.calculator.DiscountCalculatorScreen
import com.example.smartcalc.ui.calculator.GstCalculatorScreen
import com.example.smartcalc.ui.calculator.PercentageCalculatorScreen
import com.example.smartcalc.ui.calculator.ProfitCalculatorScreen
import com.example.smartcalc.ui.history.HistoryScreen
import com.example.smartcalc.ui.home.HomeScreen
import com.example.smartcalc.ui.settings.SettingsScreen

/**
 * Main Navigation Graph for Smart Calculator India.
 */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onCalculatorClick = { calculatorType ->
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        val route = when (calculatorType) {
                            CalculatorType.BASIC -> Screen.BasicCalculator.route
                            CalculatorType.GST -> Screen.GstCalculator.route
                            CalculatorType.DISCOUNT -> Screen.DiscountCalculator.route
                            CalculatorType.PROFIT_LOSS -> Screen.ProfitCalculator.route
                            CalculatorType.PERCENTAGE -> Screen.PercentageCalculator.route
                            CalculatorType.HISTORY -> Screen.History.route
                        }
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                },
                onSettingsClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate(Screen.Settings.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable(Screen.BasicCalculator.route) {
            BasicCalculatorScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.GstCalculator.route) {
            GstCalculatorScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.DiscountCalculator.route) {
            DiscountCalculatorScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.ProfitCalculator.route) {
            ProfitCalculatorScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.PercentageCalculator.route) {
            PercentageCalculatorScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}
