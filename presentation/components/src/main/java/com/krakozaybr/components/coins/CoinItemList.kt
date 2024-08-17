package com.krakozaybr.components.coins

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.currency.CurrencyChipList
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.random.Random

@Composable
fun CoinItemList(
    onCoinClick: (CoinInfo) -> Unit,
    currency: Currency,
    coinList: ImmutableList<CoinInfo>,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(vertical = AppTheme.sizes.screenPadding),
    spacing: Dp = 0.dp
) {
    LazyColumn(
        contentPadding = contentPaddingValues,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(coinList, key = { it.id }) {
            CoinItem(
                modifier = Modifier
                    .clickable { onCoinClick(it) }
                    .padding(
                        vertical = 12.dp,
                        horizontal = AppTheme.sizes.screenPadding
                    ),
                currency = currency,
                coin = it,
            )
        }
    }
}


@Preview
@Composable
private fun CoinItemListPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            CoinItemList(
                onCoinClick = {},
                currency = Currency("RUB"),
                coinList = List(10) {
                    CoinInfo(
                        id = "id$it",
                        priceChangePercentage = Math.pow(-1.0, it.toDouble()).toFloat() * it * 2,
                        name = "$it name",
                        imageLink = "https://t4.ftcdn.net/jpg/03/16/24/49/360_F_316244961_4Kch7qlXUf8accn4wXUK4vA4ZfPMmpPh.jpg",
                        price = it * 1000f,
                        symbol = "${it}SB"
                    )
                }.toImmutableList()
            )
        }
    }
}

