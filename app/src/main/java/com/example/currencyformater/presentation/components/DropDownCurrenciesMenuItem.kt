package com.example.currencyformater.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.theme.LocalTheme

@Composable
fun DropDownCurrenciesMenuItem(
    modifier: Modifier = Modifier,
    listMenu: List<CurrencyRateData>,
    onCurrencyChange: (CurrencyRateData) -> Unit
) {

    val expanded = remember {
        mutableStateOf(false)
    }

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    val icon = remember {
        derivedStateOf {
            if (expanded.value)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown
        }
    }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .defaultMinSize(minWidth = 76.dp)
    ) {
        Card(border = BorderStroke(1.dp, LocalTheme.colors.Platinum)) {
            Row(
                modifier = Modifier
                    .background(
                        color = LocalTheme.colors.White,
                        shape = RoundedCornerShape(LocalTheme.radius.radius_6dp)
                    )
                    .clickable(
                        onClick = { expanded.value = true }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        start = LocalTheme.spacing.padding_6dp
                    ),
                    text = listMenu[selectedIndex.value].name,
                    style = LocalTheme.typography.REGULAR_14_MONT,
                    color = LocalTheme.colors.WarmBlack
                )

                Icon(
                    modifier = Modifier.padding(
                        start = LocalTheme.spacing.padding_4dp,
                        end = LocalTheme.spacing.padding_6dp
                    ),
                    imageVector = icon.value,
                    contentDescription = "Currencies List"
                )
            }
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(
                    color = LocalTheme.colors.White,
                    shape = RoundedCornerShape(LocalTheme.radius.radius_8dp)
                )
        ) {
            listMenu.forEachIndexed { index, currency ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    onCurrencyChange(currency)
                    expanded.value = false
                }) {
                    Text(
                        text = currency.name,
                        style = LocalTheme.typography.BOLD_12_MONT,
                        color = LocalTheme.colors.WarmBlack
                    )
                }
            }
        }

    }
}

@Composable
@Preview
private fun DropDownCurrenciesMenuItemPreview() {
    DropDownCurrenciesMenuItem(
        listMenu = listOf(
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3),
            CurrencyRateData("EUR", 1.3)
        ),
        onCurrencyChange = {})
}