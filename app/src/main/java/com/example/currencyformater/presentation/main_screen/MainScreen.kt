package com.example.currencyformater.presentation.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.currencyformater.domain.model.BalanceListingData
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.presentation.MainViewModel
import com.example.currencyformater.presentation.components.BalancesSectionItem
import com.example.currencyformater.presentation.components.CurrencyConverterSectionItem
import com.example.currencyformater.theme.LocalTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val state: LazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
            contentPadding = PaddingValues(
                top = LocalTheme.spacing.padding_8dp,
                bottom = LocalTheme.spacing.padding_8dp
            )
        ) {
            item {
                BalancesSectionItem(
                    modifier = Modifier.padding(
                        top = LocalTheme.spacing.padding_22dp
                    ),
                    headerText = "My Balances",
                    headerTextColor = LocalTheme.colors.White,
                    headerTextStyle = LocalTheme.typography.BOLD_16_MONT,
                    balances = listOf(
                        BalanceListingData("EUR", 1000.0),
                        BalanceListingData("EUR", 1000.0),
                        BalanceListingData("EUR", 1000.0),
                        BalanceListingData("EUR", 1000.0),
                    )
                )
            }
            item {
                CurrencyConverterSectionItem(
                    modifier = Modifier.padding(
                        top = LocalTheme.spacing.padding_22dp, start = LocalTheme.spacing.padding_16dp,
                        end = LocalTheme.spacing.padding_16dp
                    ),
                    headerText = "Currency Exchange",
                    headerTextColor = LocalTheme.colors.White,
                    headerTextStyle = LocalTheme.typography.BOLD_16_MONT,
                    currencies = listOf(
                        CurrencyRateData("EUR", 1.4),
                        CurrencyRateData("EUR", 1.4),
                        CurrencyRateData("EUR", 1.4),
                        CurrencyRateData("EUR", 1.4),
                        CurrencyRateData("EUR", 1.4),
                        CurrencyRateData("EUR", 1.4)
                    )
                )
            }
        }

    }
}