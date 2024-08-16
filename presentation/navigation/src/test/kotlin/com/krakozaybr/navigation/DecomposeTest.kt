package com.krakozaybr.navigation

import kotlinx.coroutines.delay
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.koin.core.context.stopKoin

interface DecomposeTest {

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup(): Unit {
            initMainDispatcher()
            disableDecomposeWarnings()
        }


        private const val ENOUGH_WAIT_TIME = 50L

    }

    suspend fun waitStore() = delay(ENOUGH_WAIT_TIME)

    @AfterEach
    fun clearKoin() {
        stopKoin()
    }

}