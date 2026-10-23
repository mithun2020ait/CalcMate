package com.example.smartcalc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.semanticColors
import com.example.smartcalc.ui.theme.spacing

/**
 * Reusable Result Card displaying calculation output and profit/loss indicator tags.
 */
@Composable
fun CalculatorResultCard(
    title: String,
    result: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    isProfit: Boolean? = null,
    badgeText: String? = null,
    onCopyClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.standard)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                if (isProfit != null || badgeText != null) {
                    val backgroundColor = when (isProfit) {
                        true -> MaterialTheme.semanticColors.profitContainer
                        false -> MaterialTheme.semanticColors.lossContainer
                        null -> MaterialTheme.colorScheme.secondaryContainer
                    }
                    val textColor = when (isProfit) {
                        true -> MaterialTheme.semanticColors.onProfitContainer
                        false -> MaterialTheme.semanticColors.onLossContainer
                        null -> MaterialTheme.colorScheme.onSecondaryContainer
                    }

                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(backgroundColor)
                            .padding(
                                horizontal = MaterialTheme.spacing.small,
                                vertical = MaterialTheme.spacing.extraSmall
                            )
                    ) {
                        Text(
                            text = badgeText ?: if (isProfit == true) "Profit" else "Loss",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            Text(
                text = result,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            if (!subtitle.isNullOrEmpty() || onCopyClick != null) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!subtitle.isNullOrEmpty()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    if (onCopyClick != null) {
                        TextButton(onClick = onCopyClick) {
                            Text(
                                text = "Copy",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorResultCardPreview() {
    SmartCalcTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CalculatorResultCard(
                title = "Total Amount Payable",
                result = "₹ 1,18,000",
                subtitle = "Includes ₹ 18,000 GST (18%)",
                isProfit = true,
                badgeText = "+18% GST",
                onCopyClick = {}
            )
        }
    }
}
