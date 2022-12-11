package com.example.currencyformater.presentation.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.presentation.main_screen.components.BalancesSectionItem
import com.example.currencyformater.presentation.main_screen.components.CurrencyConverterSectionItem
import com.example.currencyformater.presentation.main_screen.components.DialogMessageItem
import com.example.currencyformater.theme.LocalTheme

@Composable
fun MainScreen(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MainViewModel = hiltViewModel()
) {

    val state: LazyListState = rememberLazyListState()
    val balancesList = viewModel.balancesList.value
    val receiveCurrencies = viewModel.receiveCurrencies.value
    val convertedAmount = viewModel.convertedAmount.value
    val errorMessage = viewModel.dialogMessage.value
    val displayDialog = viewModel.showDialog.value

    val changeBaseCurrency: (currencyName : String) -> Unit =
        remember(viewModel) {
            { currencyName ->
                viewModel.changeBaseCurrency(currencyName)
            }
        }

    val submitTransaction: (amount : String, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) -> Unit =
        remember(viewModel) {
            {amount, fromCurrency, toCurrency ->
                viewModel.submitConvert(amount,fromCurrency, toCurrency)
            }
        }

    val amountEntered : (amount: String, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) -> Unit =
        remember(viewModel) {
            { amount, fromCurrency, toCurrency ->
                viewModel.convertOnFlyTheAmount(amount, fromCurrency, toCurrency)
            }
        }

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume()
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.onPause()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (displayDialog){
            DialogMessageItem(message = errorMessage, onDismissRequest = viewModel::changeDialogStatus )
        }

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
                    balances = balancesList
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

                    amountConverted = convertedAmount.toString(),
                    headerTextStyle = LocalTheme.typography.BOLD_16_MONT,
                    currencies = receiveCurrencies,
                    submitTransaction = submitTransaction,
                    amountEntered = amountEntered,
                    changeBaseCurrency = changeBaseCurrency
                )
            }
        }

    }
}