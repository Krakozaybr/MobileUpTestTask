package com.krakozaybr.navigation.coin_list_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.navigation.extensions.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCoinListScreenComponent internal constructor(
    private val storeFactory: CoinListScreenStoreFactory,
    private val showDetails: (CoinInfo) -> Unit,
    componentContext: ComponentContext
) : CoinListScreenComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create()
    }

    private val scope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<State> = store.stateFlow

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is CoinListScreenStore.Label.ShowDetails -> showDetails(it.coin)
                }
            }
        }
    }

    override fun onSelectCurrency(currency: Currency) {
        store.accept(Intent.ChooseCurrency(currency))
    }

    override fun onShowDetails(coinInfo: CoinInfo) {
        store.accept(Intent.ShowDetails(coinInfo))
    }

    class Factory internal constructor(
        private val storeFactory: CoinListScreenStoreFactory,
    ) {

        fun create(
            componentContext: ComponentContext,
            showDetails: (CoinInfo) -> Unit,
        ) = DefaultCoinListScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            showDetails = showDetails
        )

    }
}