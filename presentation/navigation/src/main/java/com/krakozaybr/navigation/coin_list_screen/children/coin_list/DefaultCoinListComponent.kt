package com.krakozaybr.navigation.coin_list_screen.children.coin_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.navigation.coin_list_screen.children.currency_list.CurrencyListStore
import com.krakozaybr.navigation.extensions.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCoinListComponent internal constructor(
    private val storeFactory: CoinListStoreFactory,
    private val onShowDetails: (CoinInfo) -> Unit,
    currency: Currency,
    componentContext: ComponentContext
) : CoinListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create(currency)
    }

    private val scope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<State> = store.stateFlow

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is CoinListStore.Label.OpenDetails -> onShowDetails(it.coinInfo)
                }
            }
        }
    }

    override fun showDetails(coinInfo: CoinInfo) {
        store.accept(CoinListStore.Intent.OpenDetails(coinInfo))
    }

    class Factory internal constructor(
        private val storeFactory: CoinListStoreFactory,
    ) {

        fun create(
            componentContext: ComponentContext,
            onShowDetails: (CoinInfo) -> Unit,
            currency: Currency,
        ) = DefaultCoinListComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onShowDetails = onShowDetails,
            currency = currency,
        )

    }
}