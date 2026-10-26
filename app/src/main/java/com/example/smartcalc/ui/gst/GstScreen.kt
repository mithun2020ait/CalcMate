package com.example.smartcalc.ui.gst

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcalc.R
import com.example.smartcalc.domain.calculator.GstCalculationMode
import com.example.smartcalc.ui.components.CalculatorCard
import com.example.smartcalc.ui.components.CalculatorInputField
import com.example.smartcalc.ui.components.CalculatorResultCard
import com.example.smartcalc.ui.components.CalculatorTopAppBar
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.spacing

/**
 * Stateful GST Calculator Screen connected to GstViewModel.
 */
@Composable
fun GstScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GstViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GstScreenContent(
        uiState = uiState,
        presetRates = viewModel.presetRates,
        onBackClick = onBackClick,
        onAmountChange = viewModel::onAmountChange,
        onPresetRateSelected = viewModel::onPresetRateSelected,
        onCustomRateChange = viewModel::onCustomRateChange,
        onModeChange = viewModel::onModeChange,
        modifier = modifier
    )
}

/**
 * Stateless GST Screen Content composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GstScreenContent(
    uiState: GstUiState,
    presetRates: List<String>,
    onBackClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onPresetRateSelected: (String) -> Unit,
    onCustomRateChange: (String) -> Unit,
    onModeChange: (GstCalculationMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CalculatorTopAppBar(
                title = stringResource(R.string.calc_gst_title),
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
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.screenHorizontalPadding)
        ) {
            // Mode Selector
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = uiState.mode == GstCalculationMode.EXCLUSIVE,
                    onClick = { onModeChange(GstCalculationMode.EXCLUSIVE) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text(text = "GST Exclusive")
                }
                SegmentedButton(
                    selected = uiState.mode == GstCalculationMode.INCLUSIVE,
                    onClick = { onModeChange(GstCalculationMode.INCLUSIVE) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text(text = "GST Inclusive")
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

            // Amount Input
            CalculatorCard {
                val label = if (uiState.mode == GstCalculationMode.EXCLUSIVE) {
                    "Base Amount"
                } else {
                    "Final Amount (GST Included)"
                }

                CalculatorInputField(
                    value = uiState.amountInput,
                    onValueChange = onAmountChange,
                    label = label,
                    prefixText = "₹",
                    placeholder = "Enter amount",
                    isError = uiState.isError && uiState.amountInput.isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

            // GST Rate Selector Card
            CalculatorCard {
                Text(
                    text = "GST Rate (%)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presetRates.forEach { rate ->
                        val isSelected = !uiState.isCustomRate && uiState.selectedRate == rate
                        FilterChip(
                            selected = isSelected,
                            onClick = { onPresetRateSelected(rate) },
                            label = { Text(text = "$rate%") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                CalculatorInputField(
                    value = uiState.customRateInput,
                    onValueChange = onCustomRateChange,
                    label = "Custom Rate (%)",
                    suffixText = "%",
                    placeholder = "Enter GST rate",
                    isError = uiState.isError && uiState.errorMessage?.contains("rate", ignoreCase = true) == true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

            // Results Section
            if (uiState.isError) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = uiState.errorMessage ?: "Invalid input",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(MaterialTheme.spacing.standard)
                    )
                }
            } else if (uiState.result != null) {
                val res = uiState.result

                CalculatorResultCard(
                    title = if (uiState.mode == GstCalculationMode.EXCLUSIVE) "Total Amount Payable" else "Net Base Amount",
                    result = if (uiState.mode == GstCalculationMode.EXCLUSIVE) res.formattedTotalAmount else res.formattedBaseAmount,
                    subtitle = "Includes ${res.formattedGstAmount} GST (${res.gstRate}%)",
                    badgeText = "${res.gstRate}% GST",
                    isProfit = true,
                    onCopyClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("GST Result", res.formattedTotalAmount)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Copied ${res.formattedTotalAmount}", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.standard))

                // Breakdown Card
                CalculatorCard {
                    Text(
                        text = "Calculation Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                    BreakdownRow(label = "Net Base Amount", value = res.formattedBaseAmount)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    BreakdownRow(label = "GST Amount (${res.gstRate}%)", value = res.formattedGstAmount)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    BreakdownRow(
                        label = "Total Amount Payable",
                        value = res.formattedTotalAmount,
                        isBold = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
        }
    }
}

@Composable
private fun BreakdownRow(
    label: String,
    value: String,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isBold) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, name = "GST Screen Dark")
@Composable
fun GstScreenDarkPreview() {
    SmartCalcTheme(darkTheme = true, dynamicColor = false) {
        GstScreenContent(
            uiState = GstUiState(),
            presetRates = listOf("0", "5", "12", "18", "28"),
            onBackClick = {},
            onAmountChange = {},
            onPresetRateSelected = {},
            onCustomRateChange = {},
            onModeChange = {}
        )
    }
}
