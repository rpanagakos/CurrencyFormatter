package com.example.currencyformater.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.currencyformater.R

val MontserratFonts = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.montserrat_italic, style = FontStyle.Italic)
)

@Immutable
data class Typography(
    val BOLD_12_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_14_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_16_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_18_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_20_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_22_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_12_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_14_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_16_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_18_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_20_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 20.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val BOLD_ITALIC_22_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 22.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_12_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_14_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_16_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_18_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_20_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val REGULAR_22_MONT: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_12_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_14_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_16_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_18_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_20_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 20.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    ),
    val ITALIC_22_MONT: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
        fontSize = 22.sp,
        lineHeight = 40.sp,
        fontFamily = MontserratFonts
    )
)

internal val LocalTypography = staticCompositionLocalOf { Typography() }