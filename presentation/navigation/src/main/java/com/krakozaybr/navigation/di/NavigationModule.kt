package com.krakozaybr.navigation.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenStoreFactory
import com.krakozaybr.navigation.coin_list_screen.DefaultCoinListScreenComponent
import com.krakozaybr.navigation.details_screen.CoinDetailsScreenStoreFactory
import com.krakozaybr.navigation.details_screen.DefaultCoinDetailsScreenComponent
import com.krakozaybr.navigation.root.DefaultRootComponent
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val navigationModule = module {
    factoryOf<StoreFactory>(::DefaultStoreFactory)

    factoryOf(::CoinListScreenStoreFactory)
    factoryOf(DefaultCoinListScreenComponent::Factory)

    factoryOf(::CoinDetailsScreenStoreFactory)
    factoryOf(DefaultCoinDetailsScreenComponent::Factory)

    factoryOf(DefaultRootComponent::Factory)
}
