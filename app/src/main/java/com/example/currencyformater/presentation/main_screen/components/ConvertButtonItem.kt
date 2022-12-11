package com.example.currencyformater.presentation.main_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.theme.LocalTheme

@Composable
fun ConvertButtonItem(
    modifier: Modifier = Modifier,
    text: String,
    buttonShape: RoundedCornerShape,
    buttonColor: Color,
    textColor: Color,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = buttonShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(
                top = LocalTheme.spacing.padding_8dp,
                bottom = LocalTheme.spacing.padding_8dp,
                start = LocalTheme.spacing.padding_16dp,
                end = LocalTheme.spacing.padding_16dp
            ),
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
@DevicePreview
private fun ConvertButtonItemPreview() {
    ConvertButtonItem(
        text = "Submit transaction",
        buttonShape = RoundedCornerShape(16.dp),
        buttonColor = LocalTheme.colors.Black,
        textColor = LocalTheme.colors.White,
        textStyle = LocalTheme.typography.BOLD_12_MONT
    ) {

    }
}