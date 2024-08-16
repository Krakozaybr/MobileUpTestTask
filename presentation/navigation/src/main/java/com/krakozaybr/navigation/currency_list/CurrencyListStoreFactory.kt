package com.krakozaybr.navigation.currency_list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.onFailure
import com.krakozaybr.domain.resource.onSuccess
import com.krakozaybr.domain.use_case.GetCurrencyListUseCase
import com.krakozaybr.navigation.currency_list.CurrencyListStore.Intent
import com.krakozaybr.navigation.currency_list.CurrencyListStore.Label
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

// State of screen
sealed interface State {

    data object Loading : State
    data object LoadFailure : State

    data class LoadSuccess(
        val currencies: ImmutableList<Currency>,
        val selected: Currency = currencies.first()
    ) : State

}


internal interface CurrencyListStore : Store<Intent, State, Label> {


    // Events from user
    sealed interface Intent {

        data class CurrencyChosen(val currency: Currency) : Intent
    }

    // One-time event sent to component
    sealed interface Label {

        // Component will notify parent about change of selected currency
        data class CurrencyChanged(val newCurrency: Currency) : Label

    }
}

internal class CurrencyListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) {

    fun create(): CurrencyListStore =
        object : CurrencyListStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CurrencyListStore",
            initialState = State.Loading,
            // Sends single action at start of work
            bootstrapper = SimpleBootstrapper(Action.StartLoading),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    // Events from system
    private sealed interface Action {

        data object StartLoading : Action

    }

    // Events that change state
    private sealed interface Msg {

        data class SelectCurrency(val newCurrency: Currency) : Msg
        data class LoadSuccess(val currencies: ImmutableList<Currency>) : Msg
        data object LoadFailure : Msg

    }

    // Executes actions and intents and sends messages and labels
    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.CurrencyChosen -> {
                    val state = state() as? State.LoadSuccess ?: throw RuntimeException(
                        SELECTED_CURRENCY_WITHOUT_LOAD
                    )
                    if (state.selected != intent.currency) {
                        publish(Label.CurrencyChanged(intent.currency))
                    }
                    dispatch(Msg.SelectCurrency(intent.currency))
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                Action.StartLoading -> scope.launch {
                    getCurrencyListUseCase().collect {
                        it.onSuccess { data ->
                            dispatch(Msg.LoadSuccess(data))
                        }.onFailure {
                            // If we already have currencies and got error
                            // keep old data else show error
                            if (state() is State.Loading) {
                                dispatch(Msg.LoadFailure)
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
                Msg.LoadFailure -> State.LoadFailure
                is Msg.LoadSuccess -> if (this is State.LoadSuccess) {
                    copy(currencies = msg.currencies)
                } else State.LoadSuccess(msg.currencies)

                is Msg.SelectCurrency -> if (this is State.LoadSuccess) {
                    copy(selected = msg.newCurrency)
                } else throw RuntimeException(SELECTED_CURRENCY_WITHOUT_LOAD)
            }
    }

    companion object {

        private const val SELECTED_CURRENCY_WITHOUT_LOAD =
            "Currency cannot be selected if load failed"

    }
}
