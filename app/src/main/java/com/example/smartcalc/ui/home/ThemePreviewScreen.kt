package com.example.smartcalc.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartcalc.R
import com.example.smartcalc.ui.components.CalculatorCard
import com.example.smartcalc.ui.components.CalculatorInputField
import com.example.smartcalc.ui.components.CalculatorOutlinedButton
import com.example.smartcalc.ui.components.CalculatorPrimaryButton
import com.example.smartcalc.ui.components.CalculatorResultCard
import com.example.smartcalc.ui.components.CalculatorTopAppBar
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.spacing

@Composable
fun ThemePreviewScreen(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("10000") }

    Scaffold(
        topBar = {
            CalculatorTopAppBar(
                title = stringResource(R.string.app_name)
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.screenHorizontalPadding)
        ) {
            Text(
                text = stringResource(R.string.app_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

            CalculatorCard {
                Text(
                    text = "Sample Input Form",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                CalculatorInputField(
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    label = "Base Amount",
                    prefixText = "₹",
                    placeholder = "Enter amount"
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CalculatorPrimaryButton(
                        text = "Calculate",
                        onClick = {},
                        icon = Icons.Default.Calculate,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    CalculatorOutlinedButton(
                        text = "Reset",
                        onClick = { amountInput = "" },
                        icon = Icons.Default.Refresh,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

            CalculatorResultCard(
                title = "Calculated Output",
                result = "₹ 11,800",
                subtitle = "Includes ₹ 1,800 GST (18%)",
                isProfit = true,
                badgeText = "+18% GST",
                onCopyClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun ThemePreviewScreenLightPreview() {
    SmartCalcTheme(darkTheme = false) {
        ThemePreviewScreen()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun ThemePreviewScreenDarkPreview() {
    SmartCalcTheme(darkTheme = true) {
        ThemePreviewScreen()
    }
}
