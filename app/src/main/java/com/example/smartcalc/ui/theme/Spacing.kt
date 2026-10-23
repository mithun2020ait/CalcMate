package com.example.smartcalc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Design tokens for consistent spacing using a 4dp scale.
 */
data class Spacing(
    val none: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val standard: Dp = 16.dp,
    val large: Dp = 20.dp,
    val extraLarge: Dp = 24.dp,
    val doubleExtraLarge: Dp = 32.dp,
    val tripleExtraLarge: Dp = 48.dp,

    // Recommended touch targets and horizontal padding
    val minTouchTarget: Dp = 48.dp,
    val screenHorizontalPadding: Dp = 16.dp,
    val screenVerticalPadding: Dp = 16.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
