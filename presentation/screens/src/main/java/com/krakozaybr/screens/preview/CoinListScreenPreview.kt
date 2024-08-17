package com.krakozaybr.screens.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.screens.CoinListScreen

@Preview
@Composable
private fun CoinListScreenPreview() {
    AppTheme {
        CoinListScreen(component = PreviewCoinListScreenComponent())
    }
}