package yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.ui.theme.worksans

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    color: ButtonColor = ButtonColor.LowLight,
    isBackspaceButton: Boolean = false,
    isDarkTheme: Boolean = true
) {
    androidx.compose.material3.TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = color.color,
            contentColor = if (color == ButtonColor.HighLight || color == ButtonColor.HighDark) Color.White else if (isDarkTheme) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .height(72.dp)
            .defaultMinSize(minWidth = 72.dp)
    ) {
        Text(
            text = text,
            fontSize = if (isBackspaceButton) 24.sp else 32.sp,
            fontFamily = worksans,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    TextButton(onClick = { }, text = "C")
}