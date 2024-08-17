package com.krakozaybr.navigation.coin_list_screen.children.coin_list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.use_case.GetCoinInfoUseCase
import com.krakozaybr.navigation.coin_list_screen.children.coin_list.CoinListStore.Intent
import com.krakozaybr.navigation.coin_list_screen.children.coin_list.CoinListStore.Label
import kotlinx.collections.immutable.ImmutableList

sealed interface State {

    data object LoadFailed : State
    data object Loading : State
    data class LoadSuccess(val data: ImmutableList<CoinInfo>) : State

}


internal interface CoinListStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class CurrencyChosen(val currency: Currency) : Intent
        data class OpenDetails(val coinInfo: CoinInfo) : Intent

    }

    sealed interface Label {

        data class OpenDetails(val coinInfo: CoinInfo) : Label

    }
}

internal class CoinListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCoinInfoUseCase: GetCoinInfoUseCase
) {

    fun create(): CoinListStore =
        object : CoinListStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CoinListStore",
            initialState = State.Loading,
            bootstrapper = SimpleBootstrapper(Action.StartLoading),
            executorFactory = CoinListStoreFactory::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object StartLoading : Action
    }

    private sealed interface Msg {

        data class LoadSuccess(val data: ImmutableList<CoinInfo>) : Msg
        data object LoadFailed : Msg

    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.CurrencyChosen ->
                is Intent.OpenDetails -> publish(Label.OpenDetails(intent.coinInfo))
            }
        }

        override fun executeAction(action: Action) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                Msg.LoadFailed -> TODO()
            }
    }
}
