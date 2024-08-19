package com.krakozaybr.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.coins.CoinItemList
import com.krakozaybr.components.currency.CurrencyChipList
import com.krakozaybr.components.error.ErrorBanner
import com.krakozaybr.components.loader.Loader
import com.krakozaybr.components.toolbars.CoinListToolbar
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenComponent
import com.krakozaybr.navigation.coin_list_screen.State.CoinState
import com.krakozaybr.navigation.coin_list_screen.State.CurrencyState.LoadFailed
import com.krakozaybr.navigation.coin_list_screen.State.CurrencyState.LoadSuccess
import com.krakozaybr.navigation.coin_list_screen.State.CurrencyState.Loading

@Composable
fun CoinListScreen(
    component: CoinListScreenComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        val model by component.model.collectAsState()

        val selectedCurrency by remember {
            derivedStateOf {
                model.selectedCurrency
            }
        }

        CoinListToolbar(
            modifier = Modifier
        ) {
            val currencyState by remember {
                derivedStateOf {
                    model.currencyState
                }
            }

            Crossfade(
                modifier = Modifier
                    .fillMaxWidth(),
                targetState = currencyState,
                label = "Currency list state crossfade"
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (it) {
                        LoadFailed -> {}
                        Loading -> Loader()
                        is LoadSuccess -> {
                            CurrencyChipList(
                                selected = selectedCurrency,
                                onChipClick = component::onSelectCurrency,
                                currencyList = it.currencies
                            )
                        }
                    }
                }
            }

        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val coinListState by remember {
                derivedStateOf {
                    model.coinState
                }
            }

            Crossfade(targetState = coinListState, label = "Coin list state crossfade") {
                when (it) {
                    CoinState.LoadFailed -> {
                        ErrorBanner(onRetryClick = component::reloadAll)
                    }

                    CoinState.Loading -> Loader()
                    is CoinState.LoadSuccess -> {
                        CoinItemList(
                            onCoinClick = component::onShowDetails,
                            currency = selectedCurrency
                                ?: throw RuntimeException("Currency cannot be null if coins are loaded"),
                            coinList = it.coins
                        )
                    }
                }
            }

        }

    }
}