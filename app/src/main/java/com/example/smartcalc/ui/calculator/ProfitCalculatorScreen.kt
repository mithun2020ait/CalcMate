package com.example.smartcalc.ui.calculator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.PlaceholderScreen

@Composable
fun ProfitCalculatorScreen(onBackClick: () -> Unit) {
    PlaceholderScreen(
        title = stringResource(R.string.calc_profit_loss_title),
        icon = Icons.Default.TrendingUp,
        onBackClick = onBackClick
    )
}
