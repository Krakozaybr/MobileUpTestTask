package com.krakozaybr.domain.di

import com.krakozaybr.domain.use_case.GetCoinDetailsUseCase
import com.krakozaybr.domain.use_case.GetCoinListUseCase
import com.krakozaybr.domain.use_case.GetCurrencyListUseCase
import com.krakozaybr.domain.use_case.ReloadCoinDetailsUseCase
import com.krakozaybr.domain.use_case.ReloadCoinsUseCase
import com.krakozaybr.domain.use_case.ReloadCurrenciesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCoinDetailsUseCase)
    factoryOf(::GetCoinListUseCase)
    factoryOf(::GetCurrencyListUseCase)

    factoryOf(::ReloadCoinDetailsUseCase)
    factoryOf(::ReloadCoinsUseCase)
    factoryOf(::ReloadCurrenciesUseCase)
}
