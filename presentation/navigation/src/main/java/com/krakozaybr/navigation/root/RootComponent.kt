package com.krakozaybr.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenComponent
import com.krakozaybr.navigation.details_screen.CoinDetailsScreenComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class CoinList(val component: CoinListScreenComponent) : Child

        data class CoinDetails(val component: CoinDetailsScreenComponent) : Child

    }

}