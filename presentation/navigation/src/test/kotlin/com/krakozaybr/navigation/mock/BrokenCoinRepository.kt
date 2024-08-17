package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BrokenCoinRepository(
    private val error: FailureReason
) : CoinRepository {

    override fun getCoins(currency: Currency): Flow<Resource<ImmutableList<CoinInfo>, FailureReason>> {
        return flowOf(Resource.Failure(error))
    }

    override fun getCoinDetails(id: String): Flow<Resource<CoinDetails, FailureReason>> {
        return flowOf(Resource.Failure(error))
    }

    override suspend fun reloadCoins(): Resource<Unit, FailureReason> {
        return Resource.Failure(error)
    }

    override suspend fun reloadCoinDetails(id: String): Resource<Unit, FailureReason> {
        return Resource.Failure(error)
    }
}