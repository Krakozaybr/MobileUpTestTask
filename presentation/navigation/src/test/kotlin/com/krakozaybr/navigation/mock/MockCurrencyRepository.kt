package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.success
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockCurrencyRepository(
    private val currencies: List<Currency>
) : CurrencyRepository {
    override fun getCurrencies(): Flow<SimpleResource<ImmutableList<Currency>>> {
        return flowOf(
            success(currencies.toImmutableList())
        )
    }

    override suspend fun reloadCurrencies(): SimpleResource<Unit> {
        return success(Unit)
    }
}