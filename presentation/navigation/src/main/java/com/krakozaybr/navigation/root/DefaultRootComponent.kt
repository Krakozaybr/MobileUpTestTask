package com.krakozaybr.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.krakozaybr.navigation.coin_list_screen.DefaultCoinListScreenComponent
import com.krakozaybr.navigation.details_screen.DefaultCoinDetailsScreenComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent internal constructor(
    private val coinListScreenFactory: DefaultCoinListScreenComponent.Factory,
    private val coinDetailsScreenFactory: DefaultCoinDetailsScreenComponent.Factory,
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        initialConfiguration = Config.CoinList,
    ) { cfg, ctx ->
        when (cfg) {
            is Config.CoinDetails -> createCoinDetails(ctx, cfg.id)
            Config.CoinList -> createCoinList(ctx)
        }
    }

    private fun createCoinList(ctx: ComponentContext) = RootComponent.Child.CoinList(
        coinListScreenFactory.create(
            componentContext = ctx,
            showDetails = {
                navigation.bringToFront(Config.CoinDetails(it.id))
            }
        )
    )

    private fun createCoinDetails(ctx: ComponentContext, id: String) =
        RootComponent.Child.CoinDetails(
            coinDetailsScreenFactory.create(
                componentContext = ctx,
                id = id,
                goBack = {
                    navigation.bringToFront(Config.CoinList)
                },
            )
        )

    @Serializable
    private sealed interface Config {

        @Serializable
        data object CoinList : Config

        @Serializable
        data class CoinDetails(val id: String) : Config

    }

    class Factory(
        private val coinListScreenFactory: DefaultCoinListScreenComponent.Factory,
        private val coinDetailsScreenFactory: DefaultCoinDetailsScreenComponent.Factory,
    ) {

        fun create(
            componentContext: ComponentContext
        ) = DefaultRootComponent(
            coinListScreenFactory = coinListScreenFactory,
            coinDetailsScreenFactory = coinDetailsScreenFactory,
            componentContext = componentContext,
        )
    }

}