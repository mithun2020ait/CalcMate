package com.example.smartcalc

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appStartsAtHomeScreen() {
        composeTestRule.onNodeWithText("Smart Calculator India").assertIsDisplayed()
        composeTestRule.onNodeWithText("Calculators").assertIsDisplayed()
    }

    @Test
    fun navigateToBasicCalculatorAndBack() {
        composeTestRule.onNodeWithText("Basic Calculator").performClick()
        composeTestRule.onNodeWithText("Coming in the next development step").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Calculators").assertIsDisplayed()
    }

    @Test
    fun navigateToGstCalculatorAndBack() {
        composeTestRule.onNodeWithText("GST Calculator").performClick()
        composeTestRule.onNodeWithText("Coming in the next development step").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Calculators").assertIsDisplayed()
    }

    @Test
    fun navigateToSettingsAndBack() {
        composeTestRule.onNodeWithContentDescription("Settings").performClick()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Calculators").assertIsDisplayed()
    }
}
