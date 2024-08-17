package com.krakozaybr.components.theme

import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val main: Color = Color(0xFFFF9F00),
    val onMain: Color = Color.White,
    val mainVariant: Color = Color(0xFFFFAD25),

    val toolbarTitle: Color = Color.Black.copy(alpha = 0.87f),
)
