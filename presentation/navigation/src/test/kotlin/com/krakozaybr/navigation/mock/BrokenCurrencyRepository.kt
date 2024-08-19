package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.failure
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BrokenCurrencyRepository(
    val error: FailureReason,
) : CurrencyRepository {
    override fun getCurrencies(): Flow<SimpleResource<ImmutableList<Currency>>> {
        return flowOf(failure(error))
    }

    override suspend fun reloadCurrencies(): SimpleResource<Unit> {
        return failure(error)
    }
}