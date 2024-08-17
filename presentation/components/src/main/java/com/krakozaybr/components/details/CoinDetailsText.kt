package com.krakozaybr.components.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.krakozaybr.components.theme.Roboto

@Composable
fun CoinDetailsText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = Roboto,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier
    )
}