package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CoinRepository

class GetCoinInfoUseCase(
    private val repository: CoinRepository
) {

    suspend operator fun invoke(id: String) = repository.getCoinDetails(id)

}