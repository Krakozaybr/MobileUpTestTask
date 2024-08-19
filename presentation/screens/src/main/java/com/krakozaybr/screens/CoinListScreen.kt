package com.krakozaybr.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.coins.CoinItemList
import com.krakozaybr.components.coins.CoinListPullToRefresh
import com.krakozaybr.components.coins.RefreshFailedMessage
import com.krakozaybr.components.currency.CurrencyChipList
import com.krakozaybr.components.error.ErrorBanner
import com.krakozaybr.components.loader.Loader
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.components.toolbars.CoinListToolbar
import com.krakozaybr.components.utils.animatePlacement
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenComponent
import com.krakozaybr.navigation.coin_list_screen.State
import com.krakozaybr.navigation.coin_list_screen.State.CoinState
import com.krakozaybr.navigation.coin_list_screen.State.CurrencyState.DefaultLoad
import com.krakozaybr.navigation.coin_list_screen.State.CurrencyState.LoadSuccess
import kotlinx.coroutines.flow.filterIsInstance


private enum class ScreenVariant {
    Loading,
    Error,
    Content;

    companion object {
        fun of(state: CoinState) = when (state) {
            CoinState.LoadFailed -> Error
            is CoinState.LoadSuccess -> Content
            CoinState.Loading -> Loading
        }
    }

}

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
                    val currencies = when (it) {
                        DefaultLoad -> DefaultLoad.currencies
                        is LoadSuccess -> it.currencies
                    }
                    CurrencyChipList(
                        modifier = Modifier.fillMaxWidth(),
                        selected = selectedCurrency,
                        onChipClick = component::onSelectCurrency,
                        currencyList = currencies
                    )
                }
            }

        }

        val screenVariant by remember {
            derivedStateOf { ScreenVariant.of(model.coinState) }
        }

        // Making crossfade only if type of screen changes, not content
        Crossfade(
            targetState = screenVariant,
            label = "Content crossfade"
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (it) {
                    ScreenVariant.Loading -> Loader()
                    ScreenVariant.Error -> ErrorBanner(onRetryClick = component::reloadAll)
                    ScreenVariant.Content -> {

                        var state by remember {
                            mutableStateOf<CoinState.LoadSuccess?>(null)
                        }

                        LaunchedEffect(Unit) {
                            snapshotFlow { model.coinState }
                                .filterIsInstance(CoinState.LoadSuccess::class)
                                .collect {
                                    state = it
                                }
                        }

                        ListContent(
                            showDetails = component::onShowDetails,
                            onRefresh = remember { component::reloadAll },
                            onCancelRefreshingFailedMessage = component::hideRefreshFailedImage,
                            state = state ?: return@Crossfade
                        )
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    showDetails: (CoinInfo) -> Unit,
    onRefresh: () -> Unit,
    onCancelRefreshingFailedMessage: () -> Unit,
    state: CoinState.LoadSuccess,
    modifier: Modifier = Modifier
) {
    val refreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .clipToBounds()
            .nestedScroll(refreshState.nestedScrollConnection)
    ) {

        CoinItemList(
            onCoinClick = showDetails,
            coinList = state.coins,
            modifier = Modifier.fillMaxSize()
        )

        CoinListPullToRefresh(
            isRefreshing = state.isRefreshing,
            state = refreshState,
            onRefresh = onRefresh,
            modifier = Modifier.align(Alignment.TopCenter).animatePlacement(),
        )

        SideEffect {
            Log.d("MVIKotlin", "recomposition content ${state.isRefreshing}")
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.sizes.screenPadding),
            visible = state.showRefreshFailedMessage,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            RefreshFailedMessage(
                onCancel = onCancelRefreshingFailedMessage,
            )
        }

    }
}