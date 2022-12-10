package com.example.currencyformater.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.theme.LocalTheme

@Composable
fun HeaderTitleItem(
    modifier: Modifier = Modifier,
    title: String,
    textColor: Color,
    textStyle: TextStyle
) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
@DevicePreview
private fun HeaderTitleItemPreview() {
    HeaderTitleItem(
        title = "Currency exchange",
        textColor = LocalTheme.colors.MountainMeadow,
        textStyle = LocalTheme.typography.REGULAR_12_MONT
    )
}