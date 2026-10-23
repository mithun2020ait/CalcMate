package com.example.smartcalc.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.spacing

/**
 * Reusable input field component for numerical and text inputs.
 */
@Composable
fun CalculatorInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    prefixText: String? = null,
    suffixText: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            placeholder = if (placeholder != null) {
                { Text(text = placeholder) }
            } else null,
            prefix = if (prefixText != null) {
                { Text(text = prefixText) }
            } else null,
            suffix = if (suffixText != null) {
                { Text(text = suffixText) }
            } else null,
            isError = isError,
            keyboardOptions = keyboardOptions,
            readOnly = readOnly,
            singleLine = true,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.extraSmall
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorInputFieldPreview() {
    SmartCalcTheme {
        CalculatorInputField(
            value = "10000",
            onValueChange = {},
            label = "Amount",
            prefixText = "₹",
            modifier = Modifier.padding(16.dp)
        )
    }
}
