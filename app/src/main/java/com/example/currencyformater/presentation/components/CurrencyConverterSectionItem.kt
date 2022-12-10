package com.example.currencyformater.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.currencyformater.R
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.theme.LocalTheme

@Composable
fun CurrencyConverterSectionItem(
    modifier: Modifier = Modifier,
    headerText: String,
    headerTextColor: Color,
    headerTextStyle: TextStyle,
    currencies: List<CurrencyRateData>
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {

        val (generalRef, buttonRef) = createRefs()

        Column(
            modifier = Modifier.constrainAs(generalRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            HeaderTitleItem(title = headerText, textColor = headerTextColor, textStyle = headerTextStyle)
            Spacer(modifier = Modifier.height(LocalTheme.spacing.padding_8dp))
            Column(
                modifier = Modifier
                    .background(
                        color = LocalTheme.colors.White,
                        shape = RoundedCornerShape(LocalTheme.radius.radius_8dp)
                    )
                    .padding(
                        start = LocalTheme.spacing.padding_8dp,
                        end = LocalTheme.spacing.padding_8dp,
                        top = LocalTheme.spacing.padding_8dp,
                        bottom = LocalTheme.spacing.padding_26dp
                    )
            ) {
                SellCurrencyItem(
                    imageBackgroundColor = LocalTheme.colors.ImperialRed,
                    imageResource = R.drawable.ic_baseline_arrow_upward_24,
                    currencies = currencies,
                    title = "Sell",
                    onSellAmountChange = {},
                    onCurrencyChange = {})
                SellCurrencyItem(
                    imageBackgroundColor = LocalTheme.colors.MaximumGreen,
                    imageResource = R.drawable.ic_baseline_arrow_downward_24,
                    textFieldEnabled = false,
                    initialText = "",
                    title = "Receive",
                    currencies = currencies,
                    onSellAmountChange = {},
                    onCurrencyChange = {})

            }
        }

        ConvertButtonItem(
            modifier = Modifier.constrainAs(buttonRef) {
                bottom.linkTo(generalRef.bottom)
                top.linkTo(generalRef.bottom)
                start.linkTo(generalRef.start)
                end.linkTo(generalRef.end)
            },
            text = "Convert Amount",
            buttonShape = RoundedCornerShape(LocalTheme.radius.radius_16dp),
            buttonColor = LocalTheme.colors.TiffanyBlue,
            textColor = LocalTheme.colors.White,
            textStyle = LocalTheme.typography.BOLD_14_MONT
        ) {

        }
    }


}

@Composable
@DevicePreview
private fun CurrencyConverterSectionItemPreview() {
    CurrencyConverterSectionItem(
        headerText = "Convert your currencies", headerTextColor = LocalTheme.colors.WarmBlack,
        headerTextStyle = LocalTheme.typography.BOLD_12_MONT, currencies = listOf(
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3)
        )
    )

}