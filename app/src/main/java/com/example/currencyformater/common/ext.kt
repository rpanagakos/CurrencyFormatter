package com.example.currencyformater.common

import kotlin.math.roundToInt

fun Double.removeExtraDigits() =
    (this * 10000).roundToInt().toDouble() / 10000
