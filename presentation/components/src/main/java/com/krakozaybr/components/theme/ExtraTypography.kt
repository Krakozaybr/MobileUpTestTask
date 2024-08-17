package com.krakozaybr.components.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.krakozaybr.components.R

val Roboto = FontFamily(
    Font(
        resId = R.font.roboto,
    ),
)

data class ExtraTypography(
    val toolbarTitle: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp
    )
)
