package com.example.smartcalc.ui.calculator

import androidx.lifecycle.ViewModel
import com.example.smartcalc.domain.calculator.BasicCalculator
import com.example.smartcalc.model.CalculationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel managing UI state and user button interactions for the Basic Calculator.
 */
class BasicCalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BasicCalculatorUiState())
    val uiState: StateFlow<BasicCalculatorUiState> = _uiState.asStateFlow()

    private var lastOperator: String? = null
    private var lastSecondOperand: String? = null

    fun onDigitClick(digit: String) {
        _uiState.update { currentState ->
            if (currentState.isError) {
                val newExp = digit
                currentState.copy(
                    expression = newExp,
                    isError = false,
                    errorMessage = null,
                    resultPreview = calculatePreview(newExp)
                )
            } else {
                if (currentState.expression.length >= 50) return@update currentState
                val newExp = currentState.expression + digit
                currentState.copy(
                    expression = newExp,
                    resultPreview = calculatePreview(newExp)
                )
            }
        }
    }

    fun onOperatorClick(operator: String) {
        _uiState.update { currentState ->
            var exp = if (currentState.isError) "" else currentState.expression
            if (exp.isEmpty()) {
                if (operator == "−" || operator == "-") {
                    exp = "−"
                } else {
                    return@update currentState
                }
            } else {
                val lastChar = exp.last()
                if (lastChar == '+' || lastChar == '−' || lastChar == '-' || lastChar == '×' || lastChar == '÷') {
                    exp = exp.dropLast(1) + operator
                } else if (lastChar == '.') {
                    exp = exp + "0" + operator
                } else {
                    exp = exp + operator
                }
            }
            currentState.copy(
                expression = exp,
                isError = false,
                errorMessage = null,
                resultPreview = calculatePreview(exp)
            )
        }
    }

    fun onDecimalClick() {
        _uiState.update { currentState ->
            var exp = if (currentState.isError) "" else currentState.expression
            val activeOperand = getActiveOperand(exp)
            if (activeOperand.contains(".")) {
                return@update currentState
            }
            exp = if (activeOperand.isEmpty() || activeOperand == "−" || activeOperand == "-") {
                exp + "0."
            } else {
                exp + "."
            }
            currentState.copy(
                expression = exp,
                isError = false,
                errorMessage = null,
                resultPreview = calculatePreview(exp)
            )
        }
    }

    fun onPercentClick() {
        _uiState.update { currentState ->
            var exp = if (currentState.isError) "" else currentState.expression
            if (exp.isEmpty()) return@update currentState
            val lastChar = exp.last()
            if (lastChar.isDigit() || lastChar == ')') {
                exp += "%"
            }
            currentState.copy(
                expression = exp,
                isError = false,
                errorMessage = null,
                resultPreview = calculatePreview(exp)
            )
        }
    }

    fun onToggleSignClick() {
        _uiState.update { currentState ->
            var exp = if (currentState.isError) "" else currentState.expression
            if (exp.isEmpty()) {
                exp = "(-"
                return@update currentState.copy(expression = exp)
            }

            val newExp = toggleActiveOperandSign(exp)
            currentState.copy(
                expression = newExp,
                isError = false,
                errorMessage = null,
                resultPreview = calculatePreview(newExp)
            )
        }
    }

    fun onClearClick() {
        lastOperator = null
        lastSecondOperand = null
        _uiState.update {
            BasicCalculatorUiState()
        }
    }

    fun onBackspaceClick() {
        _uiState.update { currentState ->
            if (currentState.isError) {
                return@update BasicCalculatorUiState()
            }
            val exp = currentState.expression
            if (exp.isEmpty()) return@update currentState
            val newExp = exp.dropLast(1)
            currentState.copy(
                expression = newExp,
                resultPreview = calculatePreview(newExp)
            )
        }
    }

    fun onEqualsClick() {
        _uiState.update { currentState ->
            var exp = currentState.expression
            if (exp.isEmpty()) {
                return@update currentState
            }

            if (!containsOperator(exp) && lastOperator != null && lastSecondOperand != null) {
                exp = exp + lastOperator + lastSecondOperand
            }

            try {
                val evaluated = BasicCalculator.evaluate(exp)
                val formatted = BasicCalculator.formatDisplayResult(evaluated)

                storeLastOperation(exp)

                val resultItem = CalculationResult(
                    expression = exp,
                    result = formatted
                )

                currentState.copy(
                    expression = formatted,
                    resultPreview = "",
                    isError = false,
                    errorMessage = null,
                    recentCalculations = listOf(resultItem) + currentState.recentCalculations
                )
            } catch (e: ArithmeticException) {
                currentState.copy(
                    isError = true,
                    errorMessage = e.message ?: "Calculation error",
                    resultPreview = ""
                )
            } catch (e: Exception) {
                currentState.copy(
                    isError = true,
                    errorMessage = "Invalid expression",
                    resultPreview = ""
                )
            }
        }
    }

    private fun calculatePreview(exp: String): String {
        if (exp.isEmpty() || !containsOperator(exp)) return ""
        val lastChar = exp.last()
        if (lastChar == '+' || lastChar == '−' || lastChar == '-' || lastChar == '×' || lastChar == '÷') return ""

        return try {
            val res = BasicCalculator.evaluate(exp)
            BasicCalculator.formatDisplayResult(res)
        } catch (e: Exception) {
            ""
        }
    }

    private fun getActiveOperand(exp: String): String {
        val operators = charArrayOf('+', '−', '-', '×', '÷')
        val lastOpIndex = exp.lastIndexOfAny(operators)
        return if (lastOpIndex == -1) exp else exp.substring(lastOpIndex + 1)
    }

    private fun containsOperator(exp: String): Boolean {
        val clean = if (exp.startsWith("−") || exp.startsWith("-")) exp.substring(1) else exp
        return clean.contains("+") || clean.contains("−") || clean.contains("-") || clean.contains("×") || clean.contains("÷")
    }

    private fun toggleActiveOperandSign(exp: String): String {
        if (exp.endsWith("(-")) {
            return exp.dropLast(2)
        }
        val activeOperand = getActiveOperand(exp)
        if (activeOperand.isEmpty()) {
            return "$exp(-"
        }
        val prefix = exp.substring(0, exp.length - activeOperand.length)
        return if (prefix.endsWith("(-") && exp.endsWith(")")) {
            val unclosedPrefix = prefix.dropLast(2)
            val operandVal = activeOperand.dropLast(1)
            unclosedPrefix + operandVal
        } else {
            "$prefix(-$activeOperand)"
        }
    }

    private fun storeLastOperation(exp: String) {
        val operators = charArrayOf('+', '−', '-', '×', '÷')
        val lastOpIndex = exp.lastIndexOfAny(operators)
        if (lastOpIndex != -1 && lastOpIndex < exp.length - 1) {
            lastOperator = exp[lastOpIndex].toString()
            lastSecondOperand = exp.substring(lastOpIndex + 1)
        }
    }
}
