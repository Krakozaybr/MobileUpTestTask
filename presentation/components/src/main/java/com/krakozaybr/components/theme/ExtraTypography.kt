package com.krakozaybr.components.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.krakozaybr.components.R

val Roboto = FontFamily(
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.roboto_semibold,
    ),
)

data class ExtraTypography(
    val toolbarTitle: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp
    ),
    val mediumText: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val capsButton: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.75.sp,
        lineHeight = 16.sp,
        fontSize = 14.sp
    ),
    val chip: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
)
