package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CoinRepository

class ReloadCoinsUseCase(
    private val coinRepository: CoinRepository
) {

    suspend operator fun invoke() = coinRepository.reloadCoins()

}