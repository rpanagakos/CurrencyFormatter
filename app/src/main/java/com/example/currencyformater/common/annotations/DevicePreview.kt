package com.example.currencyformater.common.annotations

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "nexus5", device = Devices.NEXUS_5)
@Preview(name = "pixel4", device = Devices.PIXEL_4)
@Preview(name = "tablet", device = Devices.TABLET)
@Preview(name = "foldable", device = Devices.FOLDABLE)
annotation class DevicePreview