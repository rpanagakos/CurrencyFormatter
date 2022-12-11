package com.example.currencyformater.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyformater.common.annotations.DevicePreview
import com.example.currencyformater.theme.LocalTheme

@Composable
fun BalanceItem(
    modifier: Modifier = Modifier,
    priceBalance: String,
    currencyBalance: String
) {

    Column(
        modifier = modifier
            .background(
                color = LocalTheme.colors.White,
                shape = RoundedCornerShape(LocalTheme.radius.radius_8dp)
            )
            .defaultMinSize(minWidth = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = LocalTheme.spacing.padding_8dp,
                    start = LocalTheme.spacing.padding_4dp,
                    end = LocalTheme.spacing.padding_4dp
                ),
            text = priceBalance,
            style = LocalTheme.typography.REGULAR_16_MONT,
            color = LocalTheme.colors.PrussianBlue
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier
                .padding(
                    start = LocalTheme.spacing.padding_4dp,
                    end = LocalTheme.spacing.padding_4dp,
                    bottom = LocalTheme.spacing.padding_8dp
                ),
            text = currencyBalance,
            style = LocalTheme.typography.BOLD_14_MONT,
            color = LocalTheme.colors.SteelTeal
        )
    }
}

@Composable
@DevicePreview
fun BalanceItemPreview() {
    BalanceItem(priceBalance = "1200$", currencyBalance = "USD")
}