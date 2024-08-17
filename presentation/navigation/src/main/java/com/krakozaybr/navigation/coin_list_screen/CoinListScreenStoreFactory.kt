package com.krakozaybr.navigation.coin_list_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.onFailure
import com.krakozaybr.domain.resource.onSuccess
import com.krakozaybr.domain.use_case.GetCoinListUseCase
import com.krakozaybr.domain.use_case.GetCurrencyListUseCase
import com.krakozaybr.domain.use_case.ReloadCoinsUseCase
import com.krakozaybr.domain.use_case.ReloadCurrenciesUseCase
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenStore.Label
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class State(
    val currencyState: CurrencyState,
    val coinState: CoinState,
    val selectedCurrency: Currency? = null
) {
    sealed interface CurrencyState {

        data object Loading : CurrencyState
        data object LoadFailed : CurrencyState
        data class LoadSuccess(val currencies: ImmutableList<Currency>) : CurrencyState

    }

    sealed interface CoinState {

        data object Loading : CoinState
        data object LoadFailed : CoinState
        data class LoadSuccess(val coins: ImmutableList<CoinInfo>) : CoinState

    }
}


internal sealed interface Intent {

    data class ChooseCurrency(val currency: Currency) : Intent
    data class ShowDetails(val coin: CoinInfo) : Intent
    data object ReloadAll : Intent

}


internal interface CoinListScreenStore : Store<Intent, State, Label> {

    sealed interface Label {

        data class ShowDetails(val coin: CoinInfo) : Label

    }
}

internal class CoinListScreenStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCoinListUseCase: GetCoinListUseCase,
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val reloadCoinsUseCase: ReloadCoinsUseCase,
    private val reloadCurrenciesUseCase: ReloadCurrenciesUseCase
) {

    fun create(): CoinListScreenStore =
        object : CoinListScreenStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CoinListScreenStore",
            initialState = State(
                coinState = State.CoinState.Loading,
                currencyState = State.CurrencyState.Loading
            ),
            bootstrapper = SimpleBootstrapper(Action.StartLoading),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object StartLoading : Action
    }

    private sealed interface Msg {

        data object CurrencyLoadFailed : Msg
        data object CoinsLoadFailed : Msg

        data class CurrencyLoaded(val currencies: ImmutableList<Currency>) : Msg
        data class CoinsLoaded(val coins: ImmutableList<CoinInfo>) : Msg
        data class CurrencyChanged(val newCurrency: Currency) : Msg

    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.ChooseCurrency -> dispatch(Msg.CurrencyChanged(intent.currency))
                is Intent.ShowDetails -> publish(Label.ShowDetails(intent.coin))
                Intent.ReloadAll -> scope.launch {
                    if (state().currencyState is State.CurrencyState.LoadFailed) {
                        reloadCurrenciesUseCase().onSuccess {
                            reloadCoinsUseCase()
                        }
                    } else {
                        reloadCoinsUseCase()
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                Action.StartLoading -> scope.launch {
                    startLoading()
                }
            }
        }

        suspend fun startLoading() {
            getCurrencyListUseCase().collectLatest {
                it.onSuccess { data ->
                    dispatch(Msg.CurrencyLoaded(data))
                }.onFailure {
                    dispatch(Msg.CurrencyLoadFailed)
                }
                // We can load coins only if selectedCurrency is available
                // and currencies are loaded
                state().selectedCurrency?.let { currency ->
                    getCoinListUseCase(currency).collect { res ->
                        res.onSuccess { data ->
                            dispatch(Msg.CoinsLoaded(data))
                        }.onFailure {
                            dispatch(Msg.CoinsLoadFailed)
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                // If currency load is failed, we cannot show currencies neither coins
                Msg.CurrencyLoadFailed -> {
                    if (currencyState !is State.CurrencyState.LoadSuccess) {
                        copy(
                            currencyState = State.CurrencyState.LoadFailed,
                            coinState = State.CoinState.LoadFailed
                        )
                    } else this
                }
                // If coins load attempt was performed, currencies are loaded, so we
                // need to show error only in coins
                Msg.CoinsLoadFailed -> {
                    if (coinState !is State.CoinState.LoadSuccess) {
                        copy(
                            coinState = State.CoinState.LoadFailed
                        )
                    } else this
                }
                // Victory!!!
                is Msg.CoinsLoaded -> copy(
                    coinState = State.CoinState.LoadSuccess(msg.coins)
                )
                // Check if selected didn`t change
                is Msg.CurrencyChanged -> if (selectedCurrency != msg.newCurrency) {
                    copy(
                        selectedCurrency = msg.newCurrency,
                        // We need to load coins one more time
                        coinState = State.CoinState.Loading
                    )
                } else this
                is Msg.CurrencyLoaded -> {
                    if (msg.currencies.isEmpty()) {
                        reduce(Msg.CurrencyLoadFailed)
                    } else {
                        copy(
                            currencyState = State.CurrencyState.LoadSuccess(msg.currencies),
                            selectedCurrency = selectedCurrency ?: msg.currencies.first()
                        )
                    }
                }
            }
    }
}
