package com.krakozaybr.navigation.coin_list_screen.children.currency_list

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.navigation.DecomposeTest
import com.krakozaybr.navigation.createComponent
import com.krakozaybr.navigation.mock.BrokenCurrencyRepository
import com.krakozaybr.navigation.mock.MockCurrencyRepository
import com.krakozaybr.navigation.mock.mockCurrencies
import com.krakozaybr.navigation.startTestKoin
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.dsl.module

class DefaultCurrencyListComponentTest : DecomposeTest {

    @Test
    fun checkStateIfRepositoryIsBroken() = runBlocking {

        val expectedError = DataError
        val expectedState = State.LoadFailure

        val repository = BrokenCurrencyRepository(expectedError)
        val component = testComponent(
            currencyRepository = repository
        )

        waitStore()

        assertEquals(
            expectedState,
            component.model.value
        )

    }

    fun testComponent(
        onCurrencyChanged: (Currency) -> Unit = {},
        currencyRepository: CurrencyRepository = MockCurrencyRepository(mockCurrencies)
    ): DefaultCurrencyListComponent {
        val koin = startTestKoin(
            module {
                single<CurrencyRepository> { currencyRepository }
            }
        )

        return createComponent {
            koin.get<DefaultCurrencyListComponent.Factory>().create(
                componentContext = it,
                onCurrencyChanged = onCurrencyChanged,
            )
        }
    }

}