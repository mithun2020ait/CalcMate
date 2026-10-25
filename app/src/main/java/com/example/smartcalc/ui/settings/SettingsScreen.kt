package com.example.smartcalc.ui.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.PlaceholderScreen

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    PlaceholderScreen(
        title = stringResource(R.string.title_settings),
        icon = Icons.Default.Settings,
        onBackClick = onBackClick
    )
}
