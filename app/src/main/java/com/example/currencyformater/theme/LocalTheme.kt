package com.example.currencyformater.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun LocalTheme(
    colors: Colors = LocalTheme.colors,
    typography: Typography = LocalTheme.typography,
    spacing: Spacing = LocalTheme.spacing,
    radius: Radius = LocalTheme.radius,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalSpacing provides spacing,
        LocalRadius provides radius
    ) {
        content()
    }
}


object LocalTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

    val radius: Radius
        @Composable
        @ReadOnlyComposable
        get() = LocalRadius.current
}