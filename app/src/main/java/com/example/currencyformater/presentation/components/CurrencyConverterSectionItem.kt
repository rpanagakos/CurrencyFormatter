package com.example.currencyformater.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.currencyformater.domain.model.CurrencyRateData

@Composable
fun CurrencyConverterSectionItem(
    headerText: String,
    headerTextColor: Color,
    headerTextStyle: TextStyle,
    currencies : List<CurrencyRateData>
) {

    Column() {
        HeaderTitleItem(title = headerText, textColor = headerTextColor, textStyle = headerTextStyle)

    }
}