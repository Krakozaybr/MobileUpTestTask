package com.krakozaybr.navigation.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.krakozaybr.navigation.coin_list_screen.children.currency_list.CurrencyListStoreFactory
import com.krakozaybr.navigation.coin_list_screen.children.currency_list.DefaultCurrencyListComponent
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val navigationModule = module {
    factoryOf<StoreFactory>(::DefaultStoreFactory)
    factoryOf(::CurrencyListStoreFactory)
    factoryOf(DefaultCurrencyListComponent::Factory)
}
