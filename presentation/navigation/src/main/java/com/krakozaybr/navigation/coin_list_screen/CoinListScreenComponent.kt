package com.krakozaybr.navigation.coin_list_screen

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import kotlinx.coroutines.flow.StateFlow


interface CoinListScreenComponent {

    val model: StateFlow<State>

    fun onSelectCurrency(currency: Currency)

    fun onShowDetails(coinInfo: CoinInfo)

    fun reloadAll()

}