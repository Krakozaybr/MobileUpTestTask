package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CurrencyRepository

class ReloadCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke() = currencyRepository.reloadCurrencies()

}