package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CoinRepository

class ReloadCoinDetailsUseCase(
    private val coinRepository: CoinRepository
) {

    suspend operator fun invoke(id: String) = coinRepository.reloadCoinDetails(id)

}