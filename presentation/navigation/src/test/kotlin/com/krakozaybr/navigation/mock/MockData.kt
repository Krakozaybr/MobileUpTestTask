package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.Currency


val mockCurrencies = List(10) {
    Currency(
        name = it.toString()
    )
}
