package com.krakozaybr.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.krakozaybr.navigation.root.RootComponent
import com.krakozaybr.navigation.root.RootComponent.Child.CoinDetails
import com.krakozaybr.navigation.root.RootComponent.Child.CoinList

@Composable
fun RootScreen(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        modifier = modifier,
        stack = component.stack
    ) {
        when (val instance = it.instance) {
            is CoinDetails -> CoinDetailsScreen(component = instance.component)
            is CoinList -> CoinListScreen(component = instance.component)
        }
    }
}