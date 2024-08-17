package com.krakozaybr.navigation.coin_list_screen

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.navigation.DecomposeTest
import com.krakozaybr.navigation.createComponent
import com.krakozaybr.navigation.mock.BrokenCoinRepository
import com.krakozaybr.navigation.mock.BrokenCurrencyRepository
import com.krakozaybr.navigation.mock.MockCoinRepository
import com.krakozaybr.navigation.mock.MockCurrencyRepository
import com.krakozaybr.navigation.mock.NeedToReloadCoinRepository
import com.krakozaybr.navigation.mock.mockCoinInfos
import com.krakozaybr.navigation.mock.mockCurrencies
import com.krakozaybr.navigation.startTestKoin
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.koin.dsl.module

class DefaultCoinListScreenComponentTest : DecomposeTest {

    @Test
    fun checkIfCurrencyRepositoryIsBroken() = runBlocking {

        val expectedError = DataError
        val expectedState = State(
            currencyState = State.CurrencyState.LoadFailed,
            coinState = State.CoinState.LoadFailed
        )

        val component = testComponent(
            currencyRepository = BrokenCurrencyRepository(error = expectedError)
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkIfNoCurrenciesProvided() = runBlocking {

        val expectedError = DataError
        val expectedState = State(
            currencyState = State.CurrencyState.LoadFailed,
            coinState = State.CoinState.LoadFailed
        )

        val component = testComponent(
            currencyRepository = MockCurrencyRepository(listOf())
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkIfCoinRepositoryIsBroken() = runBlocking {

        val expectedError = DataError
        val expectedState = State(
            currencyState = State.CurrencyState.LoadSuccess(mockCurrencies.toImmutableList()),
            coinState = State.CoinState.LoadFailed,
            selectedCurrency = mockCurrencies.first()
        )

        val component = testComponent(
            coinRepository = BrokenCoinRepository(error = expectedError)
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkIfEverythingWorks() = runBlocking {

        val expectedState = State(
            currencyState = State.CurrencyState.LoadSuccess(mockCurrencies.toImmutableList()),
            coinState = State.CoinState.LoadSuccess(mockCoinInfos.toImmutableList()),
            selectedCurrency = mockCurrencies.first()
        )

        val component = testComponent()

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @Test
    fun checkShowDetailsCallbackWorks() = runBlocking {

        val expectedCount = 10

        var i = 0
        val component = testComponent(
            showDetails = {
                i++
            }
        )

        waitStore()

        repeat(expectedCount) {
            component.onShowDetails(mockCoinInfos.random())
            waitStore()
        }

        assertEquals(
            expectedCount,
            i
        )

    }

    @Test
    fun checkReloadWorks() = runBlocking {

        val needToReload = 12
        val expectedState = State(
            currencyState = State.CurrencyState.LoadSuccess(mockCurrencies.toImmutableList()),
            coinState = State.CoinState.LoadSuccess(mockCoinInfos.toImmutableList()),
            selectedCurrency = mockCurrencies.first()
        )

        val component = testComponent(
            coinRepository = NeedToReloadCoinRepository(
                data = mockCoinInfos,
                reloadsToWork = needToReload,
                error = DataError
            )
        )

        waitStore()

        repeat(needToReload) {
            component.reloadAll()
            waitStore()
        }

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    @RepeatedTest(10)
    fun checkSelectingCurrencyWorks() = runBlocking {

        val expectedCurrency = mockCurrencies.random()

        val component = testComponent()

        waitStore()

        component.onSelectCurrency(expectedCurrency)

        waitStore()

        assertEquals(
            expectedCurrency,
            component.model.value.selectedCurrency
        )

    }

    fun testComponent(
        showDetails: (CoinInfo) -> Unit = {},
        coinRepository: CoinRepository = MockCoinRepository(mockCoinInfos),
        currencyRepository: CurrencyRepository = MockCurrencyRepository(mockCurrencies)
    ): DefaultCoinListScreenComponent {
        val koin = startTestKoin(
            module {
                single<CoinRepository> { coinRepository }
                single<CurrencyRepository> { currencyRepository }
            }
        )

        return createComponent {
            koin.get<DefaultCoinListScreenComponent.Factory>().create(
                componentContext = it,
                showDetails = showDetails
            )
        }
    }

}