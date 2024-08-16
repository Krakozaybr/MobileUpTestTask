package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CoinRepository

class GetCoinListUseCase(
    private val repository: CoinRepository
) {

    suspend operator fun invoke() = repository.getCoins()

}