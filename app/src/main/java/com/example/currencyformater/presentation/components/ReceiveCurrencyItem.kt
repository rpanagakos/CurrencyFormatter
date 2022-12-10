package com.example.currencyformater.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.currencyformater.R
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.theme.LocalTheme

@Composable
fun ReceiveCurrencyItem(
    modifier: Modifier = Modifier,
    imageBackgroundColor: Color,
    imageResource: Int,
    title: String,
    amountConverted: String = "",
    currencies: List<CurrencyRateData>,
    onCurrencyChange: (CurrencyRateData) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(top = LocalTheme.spacing.padding_8dp),
            style = LocalTheme.typography.BOLD_14_MONT,
            color = LocalTheme.colors.PrussianBlue,
            text = title
        )
        Row(
            modifier = modifier
                .padding(top = LocalTheme.spacing.padding_8dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = imageBackgroundColor)
            )
            Text(
                modifier = Modifier
                    .padding(start = LocalTheme.spacing.padding_22dp)
                    .weight(1f),
                style = LocalTheme.typography.REGULAR_16_MONT,
                color = LocalTheme.colors.Black,
                text = amountConverted
            )
            DropDownCurrenciesMenuItem(
                modifier = Modifier.padding(start = LocalTheme.spacing.padding_6dp),
                listMenu = currencies,
                onCurrencyChange = onCurrencyChange
            )
        }
    }
}

@Composable
@DevicePreview
private fun ReceiveCurrencyItemPreview() {
    SellCurrencyItem(
        modifier = Modifier.background(color = LocalTheme.colors.White),
        imageBackgroundColor = LocalTheme.colors.ImperialRed,
        imageResource = R.drawable.ic_baseline_arrow_downward_24,
        textFieldEnabled = false,
        title = "Receive:",
        initialText = "",
        currencies = listOf(
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3)
        ), onSellAmountChange = {}, onCurrencyChange = {})
}