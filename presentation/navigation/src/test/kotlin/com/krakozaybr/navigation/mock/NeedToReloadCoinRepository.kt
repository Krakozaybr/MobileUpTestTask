package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.failure
import com.krakozaybr.domain.resource.mapData
import com.krakozaybr.domain.resource.success
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class NeedToReloadCoinRepository(
    private val data: List<CoinInfo>,
    private val reloadsToWork: Int,
    private val error: FailureReason
) : CoinRepository {

    private var attempts = 0

    private val dataFlow =
        MutableStateFlow<SimpleResource<ImmutableList<CoinInfo>>>(failure(error))

    override fun getCoins(currency: Currency): Flow<SimpleResource<ImmutableList<CoinInfo>>> {
        return dataFlow.asStateFlow()
    }

    override fun getCoinDetails(id: String): Flow<SimpleResource<CoinDetails>> {
        return dataFlow
            .map {
                it.mapData { res ->
                    res.first { info -> info.id == id }.mockMap()
                }
            }
    }

    override suspend fun reloadCoins(currency: Currency): SimpleResource<Unit> {
        attempts++
        if (attempts == reloadsToWork) {
            dataFlow.value = success(data.toImmutableList())
        }
        if (attempts >= reloadsToWork) {
            return success(Unit)
        }
        return failure(error)
    }

    override suspend fun reloadCoinDetails(id: String): SimpleResource<Unit> {
        attempts++
        if (attempts == reloadsToWork) {
            dataFlow.value = success(data.toImmutableList())
        }
        if (attempts >= reloadsToWork) {
            return success(Unit)
        }
        return failure(error)
    }
}