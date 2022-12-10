package com.example.currencyformater.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val default : Dp = 0.dp,
    val padding_2dp : Dp = 2.dp,
    val padding_4dp : Dp = 4.dp,
    val padding_6dp : Dp = 6.dp,
    val padding_8dp : Dp = 8.dp,
    val padding_10dp : Dp = 10.dp,
    val padding_12dp : Dp = 12.dp,
    val padding_14dp : Dp = 14.dp,
    val padding_16dp : Dp = 16.dp,
    val padding_18dp : Dp = 18.dp,
    val padding_20dp : Dp = 20.dp,
    val padding_22dp : Dp = 22.dp,
    val padding_24dp : Dp = 24.dp,
    val padding_26dp : Dp = 26.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }