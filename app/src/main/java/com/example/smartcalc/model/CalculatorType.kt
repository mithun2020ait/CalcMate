package com.example.smartcalc.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartcalc.R

/**
 * Enumeration representing available calculator modes in Smart Calculator India.
 */
enum class CalculatorType(
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector
) {
    BASIC(
        titleResId = R.string.calc_basic_title,
        descriptionResId = R.string.calc_basic_desc,
        icon = Icons.Default.Calculate
    ),
    GST(
        titleResId = R.string.calc_gst_title,
        descriptionResId = R.string.calc_gst_desc,
        icon = Icons.Default.Receipt
    ),
    DISCOUNT(
        titleResId = R.string.calc_discount_title,
        descriptionResId = R.string.calc_discount_desc,
        icon = Icons.Default.LocalOffer
    ),
    PROFIT_LOSS(
        titleResId = R.string.calc_profit_loss_title,
        descriptionResId = R.string.calc_profit_loss_desc,
        icon = Icons.Default.TrendingUp
    ),
    PERCENTAGE(
        titleResId = R.string.calc_percentage_title,
        descriptionResId = R.string.calc_percentage_desc,
        icon = Icons.Default.Percent
    ),
    HISTORY(
        titleResId = R.string.calc_history_title,
        descriptionResId = R.string.calc_history_desc,
        icon = Icons.Default.History
    )
}
