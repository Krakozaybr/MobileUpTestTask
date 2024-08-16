package com.krakozaybr.domain.use_case

import com.krakozaybr.domain.repository.CurrencyRepository

class GetCurrencyListUseCase(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke() = repository.getCurrencies()

}