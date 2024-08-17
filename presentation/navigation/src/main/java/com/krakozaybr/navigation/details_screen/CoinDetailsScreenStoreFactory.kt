package com.krakozaybr.navigation.details_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.resource.onFailure
import com.krakozaybr.domain.resource.onSuccess
import com.krakozaybr.domain.use_case.GetCoinDetailsUseCase
import com.krakozaybr.domain.use_case.ReloadCoinDetailsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface Intent {

    data object RetryLoading : Intent
    data object GoBack : Intent

}


sealed interface State {
    data object Loading : State
    data object LoadFailed : State
    data class LoadSuccess(val coinDetails: CoinDetails) : State
}


sealed interface Label {
    data object GoBack : Label
}

internal interface CoinDetailsScreenStore : Store<Intent, State, Label>

internal class CoinDetailsScreenStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val reloadCoinDetailsUseCase: ReloadCoinDetailsUseCase
) {

    fun create(
        id: String
    ): CoinDetailsScreenStore =
        object : CoinDetailsScreenStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CoinDetailsScreenStore",
            initialState = State.Loading,
            bootstrapper = SimpleBootstrapper(Action.StartLoading),
            executorFactory = { ExecutorImpl(id) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object StartLoading : Action
    }

    private sealed interface Msg {

        data class LoadSuccess(val coinDetails: CoinDetails) : Msg
        data object LoadFailed : Msg
        data object RetryLoad : Msg

    }

    private inner class ExecutorImpl(
        private val id: String
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.GoBack -> publish(Label.GoBack)
                Intent.RetryLoading -> {
                    dispatch(Msg.RetryLoad)
                    scope.launch {
                        reloadCoinDetailsUseCase(id)
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                Action.StartLoading -> scope.launch {
                    getCoinDetailsUseCase(id).collectLatest {
                        it.onFailure {
                            dispatch(Msg.LoadFailed)
                        }.onSuccess { data ->
                            dispatch(Msg.LoadSuccess(data))
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                Msg.LoadFailed -> State.LoadFailed
                is Msg.LoadSuccess -> State.LoadSuccess(msg.coinDetails)
                Msg.RetryLoad -> State.Loading
            }
    }
}
