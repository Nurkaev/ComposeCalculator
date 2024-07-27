package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.model

import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.CalculatorOperator

data class CalculatorState(
    val number1: String = "0",
    val number2: String = "",
    val operator: CalculatorOperator? = null
)