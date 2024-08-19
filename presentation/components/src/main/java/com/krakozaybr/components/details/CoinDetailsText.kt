package com.krakozaybr.components.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.krakozaybr.components.theme.Roboto
import de.charlex.compose.material3.HtmlText

@Composable
fun CoinDetailsText(
    text: String,
    modifier: Modifier = Modifier
) {
    HtmlText(
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