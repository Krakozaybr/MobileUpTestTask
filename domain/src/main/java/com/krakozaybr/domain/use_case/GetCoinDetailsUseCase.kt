package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CoinRepository

class GetCoinDetailsUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(id: String) = repository.getCoinDetails(id)

}