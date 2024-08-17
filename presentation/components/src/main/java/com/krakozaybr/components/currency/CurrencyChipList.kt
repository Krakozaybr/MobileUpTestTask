package com.krakozaybr.components.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.components.toolbars.CoinDetailsToolbar
import com.krakozaybr.domain.model.Currency
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CurrencyChipList(
    selected: Currency,
    onChipClick: (Currency) -> Unit,
    currencyList: ImmutableList<Currency>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(AppTheme.sizes.screenPadding),
    spacing: Dp = AppTheme.sizes.screenPadding
) {
    LazyRow(
        modifier = modifier,
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(currencyList, key = { it.name }) {
            CurrencyChip(
                isSelected = it == selected,
                name = it.name,
                onClick = { onChipClick(it) }
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyChipListPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            var selected by remember {
                mutableStateOf(Currency("Item 1"))
            }
            CurrencyChipList(
                selected = selected,
                onChipClick = { selected = it },
                currencyList = List(10) { Currency("Item $it") }.toImmutableList()
            )
        }
    }
}
