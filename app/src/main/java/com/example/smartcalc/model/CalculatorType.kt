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
 * Enumeration representing available calculator modes in Smart Calculator India.
 */
enum class CalculatorType(
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector,
    val iconColor:  Color
) {
    BASIC(
        titleResId = R.string.calc_basic_title,
        descriptionResId = R.string.calc_basic_desc,
        icon = Icons.Default.Calculate,
        iconColor = Color(0xFF5B8DEF)
    ),
    GST(
        titleResId = R.string.calc_gst_title,
        descriptionResId = R.string.calc_gst_desc,
        icon = Icons.Default.Receipt,
        iconColor = Color(0xFF26C6A0)
    ),
    DISCOUNT(
        titleResId = R.string.calc_discount_title,
        descriptionResId = R.string.calc_discount_desc,
        icon = Icons.Default.LocalOffer,
        iconColor = Color(0xFFFFA726)
    ),
    PROFIT_LOSS(
        titleResId = R.string.calc_profit_loss_title,
        descriptionResId = R.string.calc_profit_loss_desc,
        icon = Icons.Default.TrendingUp,
        iconColor = Color(0xFF9C6ADE)
    ),
    PERCENTAGE(
        titleResId = R.string.calc_percentage_title,
        descriptionResId = R.string.calc_percentage_desc,
        icon = Icons.Default.Percent,
        iconColor = Color(0xFFE94B7A)
    ),
    HISTORY(
        titleResId = R.string.calc_history_title,
        descriptionResId = R.string.calc_history_desc,
        icon = Icons.Default.History,
        iconColor = Color(0xFF42A5F5)
    )
}
