package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository

class GetCoinListUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(currency: Currency) = repository.getCoins(currency)

}