package com.krakozaybr.mobileuptesttask

import android.app.Application
import com.krakozaybr.data.di.ApplicationScope
import com.krakozaybr.data.di.dataModule
import com.krakozaybr.domain.di.domainModule
import com.krakozaybr.navigation.di.navigationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App : Application() {

    private val scope by lazy {
        CoroutineScope(
            SupervisorJob() + Dispatchers.Default
        )
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                dataModule,
                navigationModule,
                domainModule,
                module {
                    single<CoroutineScope> (
                        qualifier = named(ApplicationScope)
                    ) {
                        scope
                    }
                }
            )
            androidContext(this@App)
        }
    }
}