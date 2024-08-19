package com.krakozaybr.data.dtos

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CoinInfoDTO(
    @SerialName("id")
    val id: String,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage: Float,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val imageLink: String,
    @SerialName("current_price")
    val price: Float,
    @SerialName("symbol")
    val symbol: String,
) {
    fun map(currency: Currency) = CoinInfo(
        id = id,
        priceChangePercentage = priceChangePercentage,
        name = name,
        imageLink = imageLink,
        price = price,
        symbol = symbol,
        currency = currency
    )
}
