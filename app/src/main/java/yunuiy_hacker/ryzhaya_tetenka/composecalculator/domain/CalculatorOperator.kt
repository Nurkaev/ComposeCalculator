package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain

sealed class CalculatorOperator(val symbol: String) {
    object Multiply : CalculatorOperator(symbol = "×")
    object Divide : CalculatorOperator(symbol = "÷")
    object Plus : CalculatorOperator(symbol = "+")
    object Minus : CalculatorOperator(symbol = "-")
}