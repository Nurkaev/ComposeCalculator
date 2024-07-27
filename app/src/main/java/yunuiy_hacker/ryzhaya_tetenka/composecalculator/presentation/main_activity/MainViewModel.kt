package yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.CalculatorAction
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.CalculatorOperator
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.model.CalculatorState
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase.AppEntryUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appEntryUseCase: AppEntryUseCase) :
    ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set
    var isDarkTheme by mutableStateOf(false)

    init {
        appEntryUseCase.readAppEntry().onEach {
            isDarkTheme = it
        }.launchIn(viewModelScope)
    }

    fun setTheme() {
        isDarkTheme = !isDarkTheme
        viewModelScope.launch {
            appEntryUseCase.saveAppEntry(isDarkTheme)
        }
    }

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> onNumber(action.number)
            is CalculatorAction.Operator -> onOperator(action.operator)
            is CalculatorAction.Clear -> onClear()
            is CalculatorAction.PlusMinus -> onPlusMinus()
            is CalculatorAction.Percent -> onPercent()
            is CalculatorAction.Decimal -> onDecimal()
            is CalculatorAction.Delete -> onDelete()
            is CalculatorAction.Calculate -> onCalculate()
        }
    }

    fun onNumber(number: Int) {
        if (state.operator == null) {
            if (state.number1.length >= NUM_MAX_LENGTH) return
            if (state.number1 == "0") state = state.copy(number1 = number.toString())
            else state = state.copy(number1 = state.number1 + number)
            return
        }
        if (state.number2.length >= NUM_MAX_LENGTH) return
        state = state.copy(number2 = state.number2 + number)
    }

    fun onOperator(operator: CalculatorOperator) {
        if (state.number1.isNotBlank()) state = state.copy(operator = operator)
    }

    fun onClear() {
        state = CalculatorState()
    }

    fun onPlusMinus() {
        if (state.operator == null) {
            if (state.number1.length >= NUM_MAX_LENGTH) return
            if (state.number1 != "0") state =
                state.copy(number1 = (-state.number1.toDouble()).toString())
            return
        }
        if (state.number2.length >= NUM_MAX_LENGTH) return
        if (state.number2.length > 0 && state.number2 != "0") state =
            state.copy(number2 = (-state.number2.toDouble()).toString())
    }

    fun onPercent() {
        if (state.operator == null) {
            if (state.number1.length >= NUM_MAX_LENGTH) return
            val result = state.number1.toDouble().div(100)
            if (Math.floor(result / 1.0) == result / 1.0) {
                state = state.copy(
                    number1 = result.toInt().toString().take(NUM_MAX_LENGTH)
                )
            } else {
                state = state.copy(
                    number1 = result.toString().take(NUM_MAX_LENGTH)
                )
            }
            return
        }
        if (state.number2.length >= NUM_MAX_LENGTH) return
        val result = state.number2.toDouble().div(100)
        if (Math.floor(result / 1.0) == result / 1.0) {
            state = state.copy(
                number2 = result.toInt().toString().take(NUM_MAX_LENGTH)
            )
        } else {
            state = state.copy(
                number2 = result.toString().take(NUM_MAX_LENGTH)
            )
        }
    }

    fun onDecimal() {
        if (state.operator == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
            return
        } else if (!state.number2.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
        }
    }

    fun onDelete() {
        when {
            state.number1.isNotBlank() -> state =
                state.copy(number1 = state.number1.dropLast(n = 1))

            state.number2.isNotBlank() -> state =
                state.copy(number2 = state.number2.dropLast(n = 2))

            state.operator != null -> state = state.copy(operator = null)
        }
    }

    fun onCalculate() {
        val number1 = state.number1.toDoubleOrNull() ?: return
        val number2 = state.number2.toDoubleOrNull() ?: return
        var result = when (state.operator) {
            is CalculatorOperator.Divide -> number1.div(number2)
            is CalculatorOperator.Minus -> number1.minus(number2)
            is CalculatorOperator.Plus -> number1.plus(number2)
            is CalculatorOperator.Multiply -> number1.times(number2)
            null -> return
        }

        if (Math.floor(result / 1.0) == result / 1.0) {
            state = state.copy(
                number1 = result.toInt().toString().take(NUM_MAX_LENGTH),
                number2 = "",
                operator = null
            )
        } else {
            state = state.copy(
                number1 = result.toString().take(NUM_MAX_LENGTH), number2 = "", operator = null
            )
        }

    }

    companion object {
        private const val NUM_MAX_LENGTH = 14
    }
}