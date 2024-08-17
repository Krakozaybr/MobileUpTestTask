package com.krakozaybr.navigation.coin_list_screen.children.coin_list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.onFailure
import com.krakozaybr.domain.resource.onSuccess
import com.krakozaybr.domain.use_case.GetCoinInfoUseCase
import com.krakozaybr.domain.use_case.GetCoinListUseCase
import com.krakozaybr.navigation.coin_list_screen.children.coin_list.CoinListStore.Intent
import com.krakozaybr.navigation.coin_list_screen.children.coin_list.CoinListStore.Label
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

sealed interface State {

    data object LoadFailed : State
    data object Loading : State
    data class LoadSuccess(val data: ImmutableList<CoinInfo>) : State

}


internal interface CoinListStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class OpenDetails(val coinInfo: CoinInfo) : Intent

    }

    sealed interface Label {

        data class OpenDetails(val coinInfo: CoinInfo) : Label

    }
}

internal class CoinListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCoinListUseCase: GetCoinListUseCase
) {

    fun create(
        currency: Currency
    ): CoinListStore =
        object : CoinListStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CoinListStore",
            initialState = State.Loading,
            bootstrapper = SimpleBootstrapper(Action.StartLoading),
            executorFactory = { ExecutorImpl(currency) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object StartLoading : Action
    }

    private sealed interface Msg {

        data class LoadSuccess(val data: ImmutableList<CoinInfo>) : Msg
        data object LoadFailed : Msg

    }

    private inner class ExecutorImpl(
        private val currency: Currency
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.OpenDetails -> publish(Label.OpenDetails(intent.coinInfo))
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                Action.StartLoading -> scope.launch {
                    getCoinListUseCase(currency).collect {
                        it.onSuccess { data ->
                            dispatch(Msg.LoadSuccess(data))
                        }.onFailure {
                            if (state() !is State.LoadSuccess) {
                                dispatch(Msg.LoadFailed)
                            }
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                Msg.LoadFailed -> if (this !is State.LoadSuccess) {
                    State.LoadFailed
                } else this
                is Msg.LoadSuccess -> State.LoadSuccess(msg.data)
            }
    }
}
