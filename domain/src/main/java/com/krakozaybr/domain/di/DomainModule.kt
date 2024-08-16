package com.krakozaybr.domain.di

import com.krakozaybr.domain.use_case.GetCoinInfoUseCase
import com.krakozaybr.domain.use_case.GetCoinListUseCase
import com.krakozaybr.domain.use_case.GetCurrencyListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCoinInfoUseCase)
    factoryOf(::GetCoinListUseCase)
    factoryOf(::GetCurrencyListUseCase)
}
