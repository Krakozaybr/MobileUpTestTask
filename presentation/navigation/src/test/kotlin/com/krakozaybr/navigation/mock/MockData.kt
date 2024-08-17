package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import kotlinx.collections.immutable.persistentListOf


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

fun CoinInfo.mockMap() = CoinDetails(
    id = id,
    imageLink = imageLink,
    description = "Details description",
    categories = persistentListOf("Category 1", "Category 2")
)
