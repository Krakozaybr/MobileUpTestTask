package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.failure
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BrokenCoinRepository(
    private val error: FailureReason
) : CoinRepository {

    override fun getCoins(currency: Currency): Flow<SimpleResource<ImmutableList<CoinInfo>>> {
        return flowOf(failure(error))
    }

    override fun getCoinDetails(id: String): Flow<SimpleResource<CoinDetails>> {
        return flowOf(failure(error))
    }

    override suspend fun reloadCoins(currency: Currency): SimpleResource<Unit> {
        return failure(error)
    }

    override suspend fun reloadCoinDetails(id: String): SimpleResource<Unit> {
        return failure(error)
    }
}