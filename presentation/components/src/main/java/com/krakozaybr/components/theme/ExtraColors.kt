package com.krakozaybr.components.theme

import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val main: Color = Color(0xFFFF9F00),
    val onMain: Color = Color.White,
    val mainVariant: Color = Color(0xFFFFAD25),

    val coinHead: Color = Color(0xFF525252),
    val coinSymbol: Color = Color(0xFF525252),
    val coinSuccess: Color = Color(0xFF2A9D8F),
    val coinError: Color = Color(0xFFEB5757),

    val toolbarTitle: Color = Color.Black.copy(alpha = 0.87f),
)
