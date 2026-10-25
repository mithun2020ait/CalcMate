package com.example.smartcalc.ui.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.PlaceholderScreen

@Composable
fun HistoryScreen(onBackClick: () -> Unit) {
    PlaceholderScreen(
        title = stringResource(R.string.calc_history_title),
        icon = Icons.Default.History,
        onBackClick = onBackClick
    )
}
