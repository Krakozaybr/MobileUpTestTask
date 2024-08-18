package com.krakozaybr.data.di

import com.krakozaybr.data.network.ApiFactory
import com.krakozaybr.data.repository.CoinRepositoryImpl
import com.krakozaybr.data.repository.CurrencyRepositoryImpl
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.repository.CurrencyRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ApplicationScope = "APPLICATION_SCOPE"

val dataModule = module {
    single<CoinRepository> {
        CoinRepositoryImpl(
            api = get(),
            externalScope = get(qualifier = named(ApplicationScope))
        )
    }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
    factory {
        ApiFactory.apiService
    }
}
