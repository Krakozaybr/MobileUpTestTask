package com.krakozaybr.navigation.currency_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.navigation.extensions.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCurrencyListComponent internal constructor(
    private val storeFactory: CurrencyListStoreFactory,
    private val onCurrencyChanged: (Currency) -> Unit,
    componentContext: ComponentContext
) : CurrencyListComponent, ComponentContext by componentContext {

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
                    is CurrencyListStore.Label.CurrencyChanged -> onCurrencyChanged(it.newCurrency)
                }
            }
        }
    }

    override fun chooseCurrency(newCurrency: Currency) {
        store.accept(CurrencyListStore.Intent.CurrencyChosen(newCurrency))
    }

    class Factory internal constructor(
        private val storeFactory: CurrencyListStoreFactory,
    ) {

        fun create(
            componentContext: ComponentContext,
            onCurrencyChanged: (Currency) -> Unit,
        ) = DefaultCurrencyListComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onCurrencyChanged = onCurrencyChanged
        )

    }
}