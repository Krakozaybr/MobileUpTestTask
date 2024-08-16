package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BrokenCurrencyRepository(
    val error: FailureReason
) : CurrencyRepository {
    override fun getCurrencies(): Flow<Resource<ImmutableList<Currency>, FailureReason>> {
        return flowOf(Resource.Failure(error))
    }
}