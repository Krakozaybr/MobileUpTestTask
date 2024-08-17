package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency


val mockCurrencies = List(10) {
    Currency(
        name = it.toString()
    )
}

val mockCoinInfos = List(10) {
    CoinInfo(
        id = it.toString(),
        priceChangePercentage = it * 12f,
        name = "$it name",
        imageLink = "$it link",
        price = it.toFloat(),
        symbol = "$it symbol"
    )
}
