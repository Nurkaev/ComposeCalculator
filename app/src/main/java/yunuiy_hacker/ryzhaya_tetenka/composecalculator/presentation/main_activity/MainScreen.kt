package yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.R
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.CalculatorAction
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.CalculatorOperator
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.ButtonColor
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.TextButton
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.ThemeSwitch
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.ui.theme.DarkBackground
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.ui.theme.LightBackground
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.ui.theme.worksans

@Composable
fun MainScreen(
    viewModel: MainViewModel, isDarkTheme: Boolean
) {
    val state = viewModel.state
    val expressionScrollState = rememberScrollState()

    Surface(color = if (isDarkTheme) DarkBackground else LightBackground) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ThemeSwitch(
                isDark = viewModel.isDarkTheme,
                onCheckedChange = {
                    viewModel.setTheme()
                })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 55.dp, end = 20.dp, bottom = 66.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = state.number1 + (state.operator?.symbol ?: "") + state.number2,
                        fontFamily = worksans,
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Light,
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(expressionScrollState),
                        textAlign = TextAlign.End,
                        lineHeight = 96.sp,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Clear)
                                },
                                text = "C",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.MediumDark else ButtonColor.MediumLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.IconButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.PlusMinus)
                                },
                                icon = R.drawable.icon_plus_minus,
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.MediumDark else ButtonColor.MediumLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = { viewModel.onAction(CalculatorAction.Percent) },
                                text = "%",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.MediumDark else ButtonColor.MediumLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(
                                        CalculatorAction.Operator(
                                            CalculatorOperator.Divide
                                        )
                                    )
                                },
                                text = "÷",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.HighDark else ButtonColor.HighLight,
                                isDarkTheme = isDarkTheme
                            )
                        }


                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(7))
                                },
                                text = "7",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(8))
                                },
                                text = "8",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(9))
                                },
                                text = "9",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(
                                        CalculatorAction.Operator(
                                            CalculatorOperator.Multiply
                                        )
                                    )
                                },
                                text = "×",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.HighDark else ButtonColor.HighLight,
                                isDarkTheme = isDarkTheme
                            )
                        }


                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(4))
                                },
                                text = "4",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(5))
                                },
                                text = "5",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(6))
                                },
                                text = "6",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(
                                        CalculatorAction.Operator(
                                            CalculatorOperator.Minus
                                        )
                                    )
                                },
                                text = "–",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.HighDark else ButtonColor.HighLight,
                                isDarkTheme = isDarkTheme
                            )
                        }


                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(1))
                                },
                                text = "1",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(2))
                                },
                                text = "2",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(3))
                                },
                                text = "3",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(
                                        CalculatorAction.Operator(
                                            CalculatorOperator.Plus
                                        )
                                    )
                                },
                                text = "+",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.HighDark else ButtonColor.HighLight,
                                isDarkTheme = isDarkTheme
                            )
                        }


                        item {
                            TextButton(
                                onClick = { viewModel.onAction(CalculatorAction.Decimal) },
                                text = ".",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Number(0))
                                },
                                text = "0",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(CalculatorAction.Delete)
                                },
                                text = "⌫",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.LowDark else ButtonColor.LowLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                        item {
                            TextButton(
                                onClick = { viewModel.onAction(CalculatorAction.Calculate) },
                                text = "=",
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) ButtonColor.HighDark else ButtonColor.HighLight,
                                isDarkTheme = isDarkTheme
                            )
                        }
                    }
                    LaunchedEffect(expressionScrollState.maxValue) {
                        expressionScrollState.scrollTo(expressionScrollState.maxValue)
                    }
                }
            }
        }
    }
}