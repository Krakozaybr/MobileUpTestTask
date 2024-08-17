package com.krakozaybr.navigation.details_screen

import kotlinx.coroutines.flow.StateFlow

interface CoinDetailsScreenComponent {

    val model: StateFlow<Model>

    fun onGoBack()

    fun retryLoading()

    data class Model(
        val title: String,
        val state: State
    )

}