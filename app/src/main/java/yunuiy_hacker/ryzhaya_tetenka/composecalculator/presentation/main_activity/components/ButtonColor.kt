package yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

sealed class ButtonColor(val color: Color) {
    object LowDark : ButtonColor(Color(0xFF2E2F38))
    object MediumDark : ButtonColor(Color(0xFF4E505F))
    object HighDark : ButtonColor(Color(0xFF4B5EFC))
    object LowLight : ButtonColor(Color(0xFFFFFFFF))
    object MediumLight : ButtonColor(Color(0xFFD2D3DA))
    object HighLight : ButtonColor(Color(0xFF4B5EFC))
}