package com.krakozaybr.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.errorhandler.onDecomposeError
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.krakozaybr.domain.di.domainModule
import com.krakozaybr.navigation.di.navigationModule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

internal fun <T : Any> createComponent(factory: (ComponentContext) -> T): T {
    val lifecycle = LifecycleRegistry()
    val component = factory(DefaultComponentContext(lifecycle = lifecycle))
    lifecycle.resume()

    return component
}

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun initMainDispatcher() {
    val dispatcher = newSingleThreadContext("Test context")
    Dispatchers.setMain(dispatcher)
}

fun disableDecomposeWarnings() {
    onDecomposeError = {}
}

fun startTestKoin(vararg module: Module): Koin {

    return startKoin {
        modules(
            navigationModule,
            domainModule,
            *module
        )
    }.koin

}
