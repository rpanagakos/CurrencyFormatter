package com.example.currencyformater.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
class Colors(
    val Transparent: Color = Color(0x00000000),
    val White: Color = Color(0xFFFFFFFF),
    val Black: Color = Color(0xFF000000),
    val DarkSkyBlue: Color = Color(0xFF74B3CE),
    val SteelTeal: Color = Color(0xFF508991),
    val PrussianBlue: Color = Color(0xFF172A3A),
    val WarmBlack: Color = Color(0xFF004346),
    val MountainMeadow: Color = Color(0xFF09BC8A),
    val Platinum: Color = Color(0xFFE5E5E5),
    val MaximumGreen: Color = Color(0xFF679436),
    val OrangePantone: Color = Color(0xFFFF5400),
    val BlueDeFrance : Color = Color(0xFF008BF8)
)

internal val LocalColors = staticCompositionLocalOf { Colors() }