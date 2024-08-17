package com.krakozaybr.navigation.details_screen

import kotlinx.coroutines.flow.StateFlow

interface CoinDetailsScreenComponent {

    val model: StateFlow<State>

    fun onGoBack()

    fun retryLoading()

}