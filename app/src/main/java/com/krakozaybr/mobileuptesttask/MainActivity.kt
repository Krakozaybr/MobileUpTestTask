package com.krakozaybr.mobileuptesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.krakozaybr.navigation.root.DefaultRootComponent
import com.krakozaybr.screens.RootScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val componentFactory: DefaultRootComponent.Factory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = componentFactory.create(
            defaultComponentContext()
        )

        setContent {
            RootScreen(component = component)
        }
    }
}
