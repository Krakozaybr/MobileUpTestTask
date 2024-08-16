package com.krakozaybr.navigation.currency_list

import com.krakozaybr.domain.model.Currency
import kotlinx.coroutines.flow.StateFlow

interface CurrencyListComponent {

    val model: StateFlow<State>

    fun chooseCurrency(newCurrency: Currency)

}