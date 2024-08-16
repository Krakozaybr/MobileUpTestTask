package com.krakozaybr.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.krakozaybr.navigation.coin_list.CoinListComponent
import com.krakozaybr.navigation.details.CoinDetailsComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class CoinList(val component: CoinListComponent) : Child

        data class CoinDetails(val component: CoinDetailsComponent) : Child

    }

}