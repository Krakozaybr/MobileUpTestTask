package com.krakozaybr.navigation.coin_list_screen.children.coin_list

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.navigation.DecomposeTest
import com.krakozaybr.navigation.createComponent
import com.krakozaybr.navigation.mock.BrokenCoinRepository
import com.krakozaybr.navigation.mock.MockCoinRepository
import com.krakozaybr.navigation.mock.mockCoinInfos
import com.krakozaybr.navigation.mock.mockCurrencies
import com.krakozaybr.navigation.startTestKoin
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.dsl.module

class DefaultCoinListComponentTest : DecomposeTest {

    @Test
    fun checkIfRepositoryIsBroken() = runBlocking {

        val expectedError = DataError
        val expectedState = State.LoadFailed

        val component = testComponent(
            repository = BrokenCoinRepository(expectedError)
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkIfRepositoryIsWorking() = runBlocking {

        val expectedList = mockCoinInfos.toImmutableList()
        val expectedState = State.LoadSuccess(expectedList)

        val component = testComponent()

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkOnShowDetailsCallbackWorks() = runBlocking {

        val expectedTimes = 10

        var actualTimes = 0
        val component = testComponent(
            onShowDetails = {
                actualTimes++
            }
        )

        waitStore()

        repeat(expectedTimes) {
            component.showDetails(mockCoinInfos.random())
            waitStore()
        }

        assertEquals(
            expectedTimes,
            actualTimes
        )

    }

    fun testComponent(
        onShowDetails: (CoinInfo) -> Unit = {},
        currency: Currency = mockCurrencies.first(),
        repository: CoinRepository = MockCoinRepository(mockCoinInfos)
    ): DefaultCoinListComponent {
        val koin = startTestKoin(
            module {
                single<CoinRepository> { repository }
            }
        )

        return createComponent {
            koin.get<DefaultCoinListComponent.Factory>().create(
                componentContext = it,
                onShowDetails = onShowDetails,
                currency = currency,
            )
        }
    }

}