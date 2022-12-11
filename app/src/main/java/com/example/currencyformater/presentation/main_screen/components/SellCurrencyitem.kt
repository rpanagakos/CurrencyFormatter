package com.example.currencyformater.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyformater.R
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.theme.LocalTheme

@Composable
fun SellCurrencyItem(
    modifier: Modifier = Modifier,
    imageBackgroundColor: Color,
    imageResource: Int,
    title: String,
    textFieldEnabled: Boolean = true,
    initialText: String = "Enter amount",
    amountEntered: String = "",
    currencies: List<CurrencyRateData>,
    onSellAmountChange: (String) -> Unit,
    onCurrencyChange: (CurrencyRateData) -> Unit
) {
    val text = remember { mutableStateOf(TextFieldValue(amountEntered)) }

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
            TextField(
                modifier = Modifier
                    .padding(start = LocalTheme.spacing.padding_8dp)
                    .weight(1f),
                value = text.value,
                enabled = textFieldEnabled,
                onValueChange = {
                    text.value = it
                    onSellAmountChange(it.text)
                },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = LocalTheme.typography.ITALIC_16_MONT,
                        text = initialText
                    )
                },
                textStyle = LocalTheme.typography.REGULAR_16_MONT,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LocalTheme.colors.White,
                    textColor = LocalTheme.colors.Black,
                    focusedIndicatorColor = LocalTheme.colors.Transparent,
                    unfocusedIndicatorColor = LocalTheme.colors.Transparent,
                    disabledIndicatorColor = LocalTheme.colors.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
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
@Preview
private fun SellCurrencyItemPreview() {
    SellCurrencyItem(
        modifier = Modifier.background(color = LocalTheme.colors.White),
        imageBackgroundColor = LocalTheme.colors.MaximumGreen,
        imageResource = R.drawable.ic_baseline_arrow_upward_24,
        title = "Sell:",
        textFieldEnabled = true,
        currencies = listOf(
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3)
        ), onSellAmountChange = {}, onCurrencyChange = {})
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