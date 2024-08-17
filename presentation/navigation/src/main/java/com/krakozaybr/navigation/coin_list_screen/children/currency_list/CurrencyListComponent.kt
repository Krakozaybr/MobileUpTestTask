package com.krakozaybr.navigation.coin_list_screen.children.currency_list

import com.krakozaybr.domain.model.Currency
import kotlinx.coroutines.flow.StateFlow

interface CurrencyListComponent {

    val model: StateFlow<State>

    fun chooseCurrency(newCurrency: Currency)

}