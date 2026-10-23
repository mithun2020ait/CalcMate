package com.example.smartcalc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class SemanticColors(
    val profit: Color,
    val profitContainer: Color,
    val onProfitContainer: Color,
    val loss: Color,
    val lossContainer: Color,
    val onLossContainer: Color
)

val LightSemanticColors = SemanticColors(
    profit = ProfitGreen,
    profitContainer = ProfitContainerLight,
    onProfitContainer = ProfitGreen,
    loss = LossRed,
    lossContainer = LossContainerLight,
    onLossContainer = LossRed
)

val DarkSemanticColors = SemanticColors(
    profit = ProfitGreenDark,
    profitContainer = ProfitContainerDark,
    onProfitContainer = ProfitGreenDark,
    loss = LossRedDark,
    lossContainer = LossContainerDark,
    onLossContainer = LossRedDark
)

val LocalSemanticColors = staticCompositionLocalOf { LightSemanticColors }

val MaterialTheme.semanticColors: SemanticColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSemanticColors.current

private val LightColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,
    secondary = Slate600,
    onSecondary = Color.White,
    secondaryContainer = Slate200,
    onSecondaryContainer = Slate900,
    tertiary = Blue700,
    onTertiary = Color.White,
    background = Slate50,
    onBackground = Slate900,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate600,
    outline = Slate300,
    outlineVariant = Slate200,
    error = LossRed,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue400,
    onPrimary = Blue900,
    primaryContainer = Blue800,
    onPrimaryContainer = Blue100,
    secondary = Slate400,
    onSecondary = Slate900,
    secondaryContainer = Slate700,
    onSecondaryContainer = Slate100,
    tertiary = Blue400,
    onTertiary = Blue900,
    background = Slate900,
    onBackground = Slate50,
    surface = Slate800,
    onSurface = Slate50,
    surfaceVariant = Slate700,
    onSurfaceVariant = Slate300,
    outline = Slate500,
    outlineVariant = Slate600,
    error = LossRedDark,
    onError = Slate900
)

@Composable
fun SmartCalcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val semanticColors = if (darkTheme) DarkSemanticColors else LightSemanticColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalSemanticColors provides semanticColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}

@Composable
fun SmartCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    SmartCalcTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
