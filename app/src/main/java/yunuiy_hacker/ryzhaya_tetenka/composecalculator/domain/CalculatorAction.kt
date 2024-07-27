package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operator(val operator: CalculatorOperator) : CalculatorAction()
    object Clear : CalculatorAction()
    object PlusMinus : CalculatorAction()
    object Percent : CalculatorAction()
    object Delete : CalculatorAction()
    object Decimal : CalculatorAction()
    object Calculate : CalculatorAction()
}