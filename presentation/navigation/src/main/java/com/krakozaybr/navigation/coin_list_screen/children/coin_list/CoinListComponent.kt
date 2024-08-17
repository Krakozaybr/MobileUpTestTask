package com.krakozaybr.navigation.coin_list_screen.children.coin_list

import com.krakozaybr.domain.model.CoinInfo
import kotlinx.coroutines.flow.StateFlow

interface CoinListComponent {

    val model: StateFlow<State>

    fun showDetails(coinInfo: CoinInfo)

}