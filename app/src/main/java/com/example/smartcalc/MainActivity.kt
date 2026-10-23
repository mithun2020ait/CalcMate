package com.example.smartcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartcalc.ui.home.HomeScreen
import com.example.smartcalc.ui.theme.SmartCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCalcTheme {
                HomeScreen()
            }
        }
    }
}
