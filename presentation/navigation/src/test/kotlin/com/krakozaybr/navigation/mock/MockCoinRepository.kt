package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockCoinRepository(
    private val data: List<CoinInfo>
) : CoinRepository {
    override fun getCoins(currency: Currency): Flow<Resource<ImmutableList<CoinInfo>, FailureReason>> {
        return flowOf(Resource.Success(data.toImmutableList()))
    }

    override fun getCoinDetails(id: String): Flow<Resource<CoinDetails, FailureReason>> {
        return data.first { it.id == id }.let {
            flowOf(
                Resource.Success(
                    data.first { it.id == id }.mockMap()
                )
            )
        }
    }

    override suspend fun reloadCoins(): Resource<Unit, FailureReason> {
        return Resource.Success(Unit)
    }

    override suspend fun reloadCoinDetails(id: String): Resource<Unit, FailureReason> {
        return Resource.Success(Unit)
    }
}