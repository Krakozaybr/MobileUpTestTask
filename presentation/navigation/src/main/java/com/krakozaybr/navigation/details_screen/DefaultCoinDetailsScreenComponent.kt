package com.krakozaybr.navigation.details_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.krakozaybr.navigation.extensions.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultCoinDetailsScreenComponent internal constructor(
    private val storeFactory: CoinDetailsScreenStoreFactory,
    private val id: String,
    private val title: String,
    private val goBack: () -> Unit,
    componentContext: ComponentContext
) : CoinDetailsScreenComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create(id)
    }

    private val scope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CoinDetailsScreenComponent.Model> = store.stateFlow
        .map {
            CoinDetailsScreenComponent.Model(
                title = title,
                state = it,
            )
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = CoinDetailsScreenComponent.Model(
                title = title,
                state = store.state
            )
        )

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    Label.GoBack -> goBack()
                }
            }
        }
    }

    override fun onGoBack() {
        store.accept(Intent.GoBack)
    }

    override fun retryLoading() {
        store.accept(Intent.RetryLoading)
    }

    class Factory internal constructor(
        private val storeFactory: CoinDetailsScreenStoreFactory,
    ) {

        fun create(
            componentContext: ComponentContext,
            id: String,
            title: String,
            goBack: () -> Unit,
        ) = DefaultCoinDetailsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            id = id,
            goBack = goBack,
            title = title
        )

    }
}