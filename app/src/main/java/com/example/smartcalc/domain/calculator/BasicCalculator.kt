package com.example.smartcalc.domain.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.ArrayDeque

/**
 * Pure Kotlin calculation engine handling expression tokenization,
 * operator precedence, BigDecimal arithmetic, percentages, and Indian number formatting.
 */
object BasicCalculator {

    private val MATH_CONTEXT = MathContext(16, RoundingMode.HALF_UP)

    /**
     * Evaluates a mathematical expression string.
     * Returns a BigDecimal result or throws ArithmeticException / IllegalArgumentException.
     */
    fun evaluate(rawExpression: String): BigDecimal {
        val sanitized = sanitizeExpression(rawExpression)
        if (sanitized.isEmpty()) {
            return BigDecimal.ZERO
        }

        val tokens = tokenize(sanitized)
        if (tokens.isEmpty()) {
            return BigDecimal.ZERO
        }

        val postfix = shuntingYard(tokens)
        return evaluatePostfix(postfix)
    }

    /**
     * Pre-processes user input expression string into a clean standard format.
     */
    fun sanitizeExpression(expression: String): String {
        return expression
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace(" ", "")
            .trim()
    }

    /**
     * Formats a BigDecimal into a readable display string with Indian number system grouping.
     * Example: 100000 -> "1,00,000", 125.50 -> "125.5"
     */
    fun formatDisplayResult(number: BigDecimal): String {
        val stripped = number.stripTrailingZeros()
        val plainString = stripped.toPlainString()
        return formatIndianNumberString(plainString)
    }

    /**
     * Formats a number string using the Indian Number System (Lakhs, Crores).
     */
    fun formatIndianNumberString(numberString: String): String {
        if (numberString.isEmpty() || numberString == "-" || numberString == ".") return numberString

        val isNegative = numberString.startsWith("-")
        val cleanString = if (isNegative) numberString.substring(1) else numberString

        val parts = cleanString.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) ".${parts[1]}" else ""

        if (integerPart.isEmpty()) {
            return (if (isNegative) "-" else "") + decimalPart
        }

        val formattedInteger = if (integerPart.length <= 3) {
            integerPart
        } else {
            val lastThree = integerPart.takeLast(3)
            val rest = integerPart.dropLast(3)
            val formattedRest = rest.reversed().chunked(2).joinToString(",").reversed()
            "$formattedRest,$lastThree"
        }

        return (if (isNegative) "-" else "") + formattedInteger + decimalPart
    }

    // Token definitions
    sealed class Token {
        data class Number(val value: BigDecimal) : Token()
        data class Operator(val symbol: Char, val precedence: Int) : Token()
        data class Percent(val isAdditive: Boolean) : Token()
        object LeftParenthesis : Token()
        object RightParenthesis : Token()
    }

    private fun tokenize(expression: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        var prevToken: Token? = null

        while (i < expression.length) {
            val c = expression[i]

            when {
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        sb.append(expression[i])
                        i++
                    }
                    val numStr = sb.toString()
                    val num = try {
                        BigDecimal(numStr)
                    } catch (e: Exception) {
                        BigDecimal.ZERO
                    }
                    val token = Token.Number(num)
                    tokens.add(token)
                    prevToken = token
                    continue
                }
                c == '(' -> {
                    tokens.add(Token.LeftParenthesis)
                    prevToken = Token.LeftParenthesis
                    i++
                }
                c == ')' -> {
                    tokens.add(Token.RightParenthesis)
                    prevToken = Token.RightParenthesis
                    i++
                }
                c == '+' || c == '-' || c == '*' || c == '/' -> {
                    // Check for unary minus e.g. "(-25)" or "-25" at start
                    if (c == '-' && (prevToken == null || prevToken is Token.LeftParenthesis || prevToken is Token.Operator)) {
                        i++ // skip '-'
                        if (i < expression.length && expression[i] == '(') {
                            tokens.add(Token.Number(BigDecimal.ONE.negate()))
                            tokens.add(Token.Operator('*', 2))
                            prevToken = Token.Operator('*', 2)
                            continue
                        } else {
                            val sb = StringBuilder("-")
                            while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                                sb.append(expression[i])
                                i++
                            }
                            val numStr = sb.toString()
                            val num = try {
                                BigDecimal(if (numStr == "-") "-0" else numStr)
                            } catch (e: Exception) {
                                BigDecimal.ZERO
                            }
                            val token = Token.Number(num)
                            tokens.add(token)
                            prevToken = token
                            continue
                        }
                    } else {
                        val precedence = when (c) {
                            '*', '/' -> 2
                            '+', '-' -> 1
                            else -> 1
                        }
                        val token = Token.Operator(c, precedence)
                        tokens.add(token)
                        prevToken = token
                        i++
                    }
                }
                c == '%' -> {
                    var isAdditive = false
                    if (tokens.size >= 2) {
                        var idx = tokens.size - 2
                        if (tokens[idx] is Token.Operator) {
                            val op = (tokens[idx] as Token.Operator).symbol
                            if (op == '+' || op == '-') {
                                isAdditive = true
                            }
                        }
                    }
                    val token = Token.Percent(isAdditive)
                    tokens.add(token)
                    prevToken = token
                    i++
                }
                else -> {
                    i++
                }
            }
        }
        return tokens
    }

    private fun shuntingYard(tokens: List<Token>): List<Token> {
        val outputQueue = mutableListOf<Token>()
        val operatorStack = ArrayDeque<Token>()

        for (token in tokens) {
            when (token) {
                is Token.Number -> outputQueue.add(token)
                is Token.Percent -> outputQueue.add(token)
                is Token.Operator -> {
                    while (!operatorStack.isEmpty()) {
                        val top = operatorStack.peek()
                        if (top is Token.Operator && top.precedence >= token.precedence) {
                            outputQueue.add(operatorStack.pop())
                        } else {
                            break
                        }
                    }
                    operatorStack.push(token)
                }
                is Token.LeftParenthesis -> operatorStack.push(token)
                is Token.RightParenthesis -> {
                    while (!operatorStack.isEmpty() && operatorStack.peek() !is Token.LeftParenthesis) {
                        outputQueue.add(operatorStack.pop())
                    }
                    if (!operatorStack.isEmpty() && operatorStack.peek() is Token.LeftParenthesis) {
                        operatorStack.pop()
                    }
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            val top = operatorStack.pop()
            if (top !is Token.LeftParenthesis && top !is Token.RightParenthesis) {
                outputQueue.add(top)
            }
        }

        return outputQueue
    }

    private fun evaluatePostfix(postfix: List<Token>): BigDecimal {
        val stack = ArrayDeque<BigDecimal>()

        for (token in postfix) {
            when (token) {
                is Token.Number -> stack.push(token.value)
                is Token.Percent -> {
                    if (stack.isEmpty()) continue
                    val valB = stack.pop()
                    if (!token.isAdditive) {
                        // Multiplicative / standalone percent: B% = B / 100
                        stack.push(valB.divide(BigDecimal("100"), MATH_CONTEXT))
                    } else {
                        // Additive percentage: B% in A + B% = (A * B) / 100
                        if (!stack.isEmpty()) {
                            val valA = stack.peek()
                            val percentAmount = valA.multiply(valB, MATH_CONTEXT).divide(BigDecimal("100"), MATH_CONTEXT)
                            stack.push(percentAmount)
                        } else {
                            stack.push(valB.divide(BigDecimal("100"), MATH_CONTEXT))
                        }
                    }
                }
                is Token.Operator -> {
                    if (stack.size < 2) continue
                    val b = stack.pop()
                    val a = stack.pop()

                    val res = when (token.symbol) {
                        '+' -> a.add(b, MATH_CONTEXT)
                        '-' -> a.subtract(b, MATH_CONTEXT)
                        '*' -> a.multiply(b, MATH_CONTEXT)
                        '/' -> {
                            if (b.compareTo(BigDecimal.ZERO) == 0) {
                                throw ArithmeticException("Cannot divide by zero")
                            }
                            a.divide(b, MATH_CONTEXT)
                        }
                        else -> BigDecimal.ZERO
                    }
                    stack.push(res)
                }
                else -> {}
            }
        }

        return if (stack.isNotEmpty()) stack.pop() else BigDecimal.ZERO
    }
}
