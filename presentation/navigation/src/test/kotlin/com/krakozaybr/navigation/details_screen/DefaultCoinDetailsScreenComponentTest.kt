package com.krakozaybr.navigation.details_screen

import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.navigation.DecomposeTest
import com.krakozaybr.navigation.createComponent
import com.krakozaybr.navigation.mock.BrokenCoinRepository
import com.krakozaybr.navigation.mock.MockCoinRepository
import com.krakozaybr.navigation.mock.MockCurrencyRepository
import com.krakozaybr.navigation.mock.NeedToReloadCoinRepository
import com.krakozaybr.navigation.mock.mockCoinInfos
import com.krakozaybr.navigation.mock.mockCurrencies
import com.krakozaybr.navigation.mock.mockMap
import com.krakozaybr.navigation.startTestKoin
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.dsl.module

class DefaultCoinDetailsScreenComponentTest : DecomposeTest {

    @Test
    fun checkIfRepositoryIsBroken() = runBlocking {

        val expectedState = State.LoadFailed
        val expectedError = DataError.Network.NO_INTERNET

        val component = testComponent(
            coinRepository = BrokenCoinRepository(expectedError)
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkIfRepositoryWorksFine() = runBlocking {

        val expectedDetails = mockCoinInfos.random().mockMap()
        val expectedState = State.LoadSuccess(expectedDetails)

        val component = testComponent(
            id = expectedDetails.id
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkGoBackCallbackWorks() = runBlocking {

        val expectedCount = 12

        var i = 0
        val component = testComponent(
            goBack = { i++ }
        )

        waitStore()

        repeat(expectedCount) {
            component.onGoBack()
            waitStore()
        }

        assertEquals(
            expectedCount,
            i
        )

    }

    @Test
    fun checkReloadWorks() = runBlocking {

        val needToReload = 22
        val expectedDetails = mockCoinInfos.random().mockMap()
        val expectedState = State.LoadSuccess(expectedDetails)

        val component = testComponent(
            id = expectedDetails.id,
            coinRepository = NeedToReloadCoinRepository(
                data = mockCoinInfos,
                reloadsToWork = needToReload,
                error = DataError.Network.NO_INTERNET
            )
        )

        waitStore()

        repeat(needToReload) {
            component.retryLoading()
            waitStore()
        }

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    fun testComponent(
        goBack: () -> Unit = {},
        id: String = mockCoinInfos[0].id,
        title: String = mockCoinInfos[0].name,
        coinRepository: CoinRepository = MockCoinRepository(mockCoinInfos),
        currencyRepository: CurrencyRepository = MockCurrencyRepository(mockCurrencies)
    ): DefaultCoinDetailsScreenComponent {
        val koin = startTestKoin(
            module {
                single<CoinRepository> { coinRepository }
                single<CurrencyRepository> { currencyRepository }
            }
        )

        return createComponent {
            koin.get<DefaultCoinDetailsScreenComponent.Factory>().create(
                componentContext = it,
                goBack = goBack,
                id = id,
                title = title
            )
        }
    }

}