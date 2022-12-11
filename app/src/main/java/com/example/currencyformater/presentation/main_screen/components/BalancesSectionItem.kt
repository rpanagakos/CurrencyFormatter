package com.example.currencyformater.presentation.main_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyformater.domain.model.BalanceListingData
import com.example.currencyformater.theme.LocalTheme

@Composable
fun BalancesSectionItem(
    modifier: Modifier = Modifier,
    headerText: String,
    headerTextColor: Color,
    headerTextStyle: TextStyle,
    balances: List<BalanceListingData>
) {
    val state: LazyListState = rememberLazyListState()

    Column(
        modifier = modifier
    ) {
        HeaderTitleItem(
            modifier = Modifier.padding(start = LocalTheme.spacing.padding_16dp),
            title = headerText,
            textColor = headerTextColor,
            textStyle = headerTextStyle)
        Spacer(modifier = Modifier.height(LocalTheme.spacing.padding_8dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(LocalTheme.spacing.padding_12dp),
            contentPadding = PaddingValues(
                start = LocalTheme.spacing.padding_16dp,
                end = LocalTheme.spacing.padding_16dp
            )
        ) {
            items(balances) { item ->
                BalanceItem(
                    modifier = Modifier
                        .fillParentMaxWidth( if (balances.size == 1) 1f else 0.33f),
                    priceBalance = item.balance.toString(),
                    currencyBalance = item.name
                )
            }
        }
    }
}


@Preview
@Composable
fun BalancesSectionItemPreview() {
    val testList = mutableListOf<BalanceListingData>()
    val title = "Currencies Balances"
    for (i in 0..30) {
        val balance = BalanceListingData("EUROOOO", 12400.0)
        testList.add(balance)
    }
    BalancesSectionItem(
        headerText = title,
        headerTextColor = LocalTheme.colors.MountainMeadow,
        headerTextStyle = LocalTheme.typography.REGULAR_12_MONT,
        balances = testList
    )
}

@Preview
@Composable
fun BalancesSectionItemPreview2() {
    val testList = mutableListOf<BalanceListingData>()
    val title = "Currencies Balances"
    for (i in 0..1) {
        val balance = BalanceListingData("EUROOOO", 12400.0)
        testList.add(balance)
    }
    BalancesSectionItem(
        headerText = title,
        headerTextColor = LocalTheme.colors.WarmBlack,
        headerTextStyle = LocalTheme.typography.BOLD_12_MONT,
        balances = testList
    )
}