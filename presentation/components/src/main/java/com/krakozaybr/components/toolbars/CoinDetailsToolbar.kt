package com.krakozaybr.components.toolbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.theme.AppTheme

@Composable
fun CoinDetailsToolbar(
    title: String,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    spacing: Dp = 20.dp,
) {
    AppToolbar(
        modifier = modifier
    ) {

        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoBackToolbarIcon(onClick = onGoBackClick)
            ToolbarText(text = title)
        }
    }
}

@Preview
@Composable
private fun CoinDetailsToolbarPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CoinDetailsToolbar(
                onGoBackClick = {},
                title = "Some title",
                modifier = Modifier
            )
        }
    }
}