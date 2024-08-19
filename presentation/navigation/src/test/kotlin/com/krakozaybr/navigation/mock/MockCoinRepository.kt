package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.success
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockCoinRepository(
    private val data: List<CoinInfo>
) : CoinRepository {
    override fun getCoins(currency: Currency): Flow<SimpleResource<ImmutableList<CoinInfo>>> {
        return flowOf(success(data.toImmutableList()))
    }

    override fun getCoinDetails(id: String): Flow<SimpleResource<CoinDetails>> {
        return data.first { it.id == id }.let {
            flowOf(
                success(
                    data.first { it.id == id }.mockMap()
                )
            )
        }
    }

    override suspend fun reloadCoins(currency: Currency): SimpleResource<Unit> {
        return success()
    }

    override suspend fun reloadCoinDetails(id: String): SimpleResource<Unit> {
        return success()
    }
}