package com.example.smartcalc.ui.calculator

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.CalculatorTopAppBar
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.spacing

/**
 * Stateful Basic Calculator Screen composable connected to ViewModel.
 */
@Composable
fun BasicCalculatorScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BasicCalculatorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BasicCalculatorScreenContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onDigitClick = viewModel::onDigitClick,
        onOperatorClick = viewModel::onOperatorClick,
        onDecimalClick = viewModel::onDecimalClick,
        onPercentClick = viewModel::onPercentClick,
        onToggleSignClick = viewModel::onToggleSignClick,
        onClearClick = viewModel::onClearClick,
        onBackspaceClick = viewModel::onBackspaceClick,
        onEqualsClick = viewModel::onEqualsClick,
        modifier = modifier
    )
}

/**
 * Stateless Basic Calculator Screen Content composable.
 */
@Composable
fun BasicCalculatorScreenContent(
    uiState: BasicCalculatorUiState,
    onBackClick: () -> Unit,
    onDigitClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onDecimalClick: () -> Unit,
    onPercentClick: () -> Unit,
    onToggleSignClick: () -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    onEqualsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CalculatorTopAppBar(
                title = stringResource(R.string.calc_basic_title),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.screenHorizontalPadding)
        ) {
            // Display Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = MaterialTheme.spacing.standard),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                // Expression Text
                val expScrollState = rememberScrollState()
                Text(
                    text = uiState.expression.ifEmpty { "0" },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(expScrollState)
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                // Result Preview or Error Message
                if (uiState.isError) {
                    Text(
                        text = uiState.errorMessage ?: "Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (uiState.resultPreview.isNotEmpty()) {
                    Text(
                        text = uiState.resultPreview,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Keypad Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.8f)
                    .padding(bottom = MaterialTheme.spacing.standard),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val digitBg = MaterialTheme.colorScheme.surfaceContainerHigh
                val digitText = MaterialTheme.colorScheme.onSurface
                val funcBg = Color(0xFF1E293B)
                val funcText = Color(0xFF94A3B8)
                val opBg = Color(0xFF1E3A8A)
                val opText = Color.White
                val equalsBg = Color(0xFF2563EB)
                val equalsText = Color.White

                // Row 1: AC, +/-, %, ÷
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KeypadButton(
                        text = "AC",
                        onClick = onClearClick,
                        containerColor = funcBg,
                        contentColor = Color(0xFFF87171),
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "+/-",
                        onClick = onToggleSignClick,
                        containerColor = funcBg,
                        contentColor = funcText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "%",
                        onClick = onPercentClick,
                        containerColor = funcBg,
                        contentColor = funcText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "÷",
                        onClick = { onOperatorClick("÷") },
                        containerColor = opBg,
                        contentColor = opText,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: 7, 8, 9, ×
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KeypadButton(
                        text = "7",
                        onClick = { onDigitClick("7") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "8",
                        onClick = { onDigitClick("8") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "9",
                        onClick = { onDigitClick("9") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "×",
                        onClick = { onOperatorClick("×") },
                        containerColor = opBg,
                        contentColor = opText,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 3: 4, 5, 6, −
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KeypadButton(
                        text = "4",
                        onClick = { onDigitClick("4") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "5",
                        onClick = { onDigitClick("5") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "6",
                        onClick = { onDigitClick("6") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "−",
                        onClick = { onOperatorClick("−") },
                        containerColor = opBg,
                        contentColor = opText,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 4: 1, 2, 3, +
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KeypadButton(
                        text = "1",
                        onClick = { onDigitClick("1") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "2",
                        onClick = { onDigitClick("2") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "3",
                        onClick = { onDigitClick("3") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "+",
                        onClick = { onOperatorClick("+") },
                        containerColor = opBg,
                        contentColor = opText,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 5: ⌫, 0, ., =
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KeypadButton(
                        text = "⌫",
                        onClick = onBackspaceClick,
                        containerColor = funcBg,
                        contentColor = funcText,
                        icon = Icons.AutoMirrored.Filled.Backspace,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "0",
                        onClick = { onDigitClick("0") },
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = ".",
                        onClick = onDecimalClick,
                        containerColor = digitBg,
                        contentColor = digitText,
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "=",
                        onClick = onEqualsClick,
                        containerColor = equalsBg,
                        contentColor = equalsText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    icon: ImageVector? = null
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier.fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Basic Calculator - Dark")
@Composable
fun BasicCalculatorScreenDarkPreview() {
    SmartCalcTheme(darkTheme = true, dynamicColor = false) {
        BasicCalculatorScreenContent(
            uiState = BasicCalculatorUiState(
                expression = "2 + 3 × 4",
                resultPreview = "14"
            ),
            onBackClick = {},
            onDigitClick = {},
            onOperatorClick = {},
            onDecimalClick = {},
            onPercentClick = {},
            onToggleSignClick = {},
            onClearClick = {},
            onBackspaceClick = {},
            onEqualsClick = {}
        )
    }
}
