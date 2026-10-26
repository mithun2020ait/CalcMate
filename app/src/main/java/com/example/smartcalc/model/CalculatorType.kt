package com.example.smartcalc.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartcalc.R

/**
 * Enumeration representing available calculator modes in Smart Calculator India / CalcMate.
 */
enum class CalculatorType(
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector,
    val accentColor: Color
) {
    BASIC(
        titleResId = R.string.calc_basic_title,
        descriptionResId = R.string.calc_basic_desc,
        icon = Icons.Default.Calculate,
        accentColor = Color(0xFF2563EB)
    ),
    GST(
        titleResId = R.string.calc_gst_title,
        descriptionResId = R.string.calc_gst_desc,
        icon = Icons.Default.Receipt,
        accentColor = Color(0xFF0D9488)
    ),
    DISCOUNT(
        titleResId = R.string.calc_discount_title,
        descriptionResId = R.string.calc_discount_desc,
        icon = Icons.Default.LocalOffer,
        accentColor = Color(0xFFEA580C)
    ),
    PROFIT_LOSS(
        titleResId = R.string.calc_profit_loss_title,
        descriptionResId = R.string.calc_profit_loss_desc,
        icon = Icons.Default.TrendingUp,
        accentColor = Color(0xFF7C3AED)
    ),
    PERCENTAGE(
        titleResId = R.string.calc_percentage_title,
        descriptionResId = R.string.calc_percentage_desc,
        icon = Icons.Default.Percent,
        accentColor = Color(0xFFE11D48)
    ),
    HISTORY(
        titleResId = R.string.calc_history_title,
        descriptionResId = R.string.calc_history_desc,
        icon = Icons.Default.History,
        accentColor = Color(0xFF0284C7)
    )
}
