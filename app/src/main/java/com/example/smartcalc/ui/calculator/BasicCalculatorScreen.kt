package com.example.smartcalc.ui.calculator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.PlaceholderScreen

@Composable
fun BasicCalculatorScreen(onBackClick: () -> Unit) {
    PlaceholderScreen(
        title = stringResource(R.string.calc_basic_title),
        icon = Icons.Default.Calculate,
        onBackClick = onBackClick
    )
}
