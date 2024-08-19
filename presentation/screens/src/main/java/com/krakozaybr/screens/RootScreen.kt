package com.krakozaybr.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.navigation.root.RootComponent
import com.krakozaybr.navigation.root.RootComponent.Child.CoinDetails
import com.krakozaybr.navigation.root.RootComponent.Child.CoinList

@Composable
fun RootScreen(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    AppTheme {
        Children(
            modifier = modifier,
            stack = component.stack,
            animation = stackAnimation(slide())
        ) {
            when (val instance = it.instance) {
                is CoinDetails -> CoinDetailsScreen(component = instance.component)
                is CoinList -> CoinListScreen(component = instance.component)
            }
        }
    }
}