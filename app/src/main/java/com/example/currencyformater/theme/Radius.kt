package com.example.currencyformater.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Radius(
    val default: Dp = 0.dp,
    val radius_2dp : Dp = 2.dp,
    val radius_4dp : Dp = 4.dp,
    val radius_6dp : Dp = 6.dp,
    val radius_8dp : Dp = 8.dp,
    val radius_10dp : Dp = 10.dp,
    val radius_12dp : Dp = 12.dp,
    val radius_14dp : Dp = 14.dp,
    val radius_16dp : Dp = 16.dp,
    val radius_18dp : Dp = 18.dp,
)

internal val LocalRadius = staticCompositionLocalOf { Radius() }