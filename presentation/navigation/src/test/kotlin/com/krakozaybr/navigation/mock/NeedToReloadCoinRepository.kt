package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.mapData
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
        MutableStateFlow<Resource<ImmutableList<CoinInfo>, FailureReason>>(Resource.Failure(error))

    override fun getCoins(currency: Currency): Flow<Resource<ImmutableList<CoinInfo>, FailureReason>> {
        return dataFlow.asStateFlow()
    }

    override fun getCoinDetails(id: String): Flow<Resource<CoinDetails, FailureReason>> {
        return dataFlow
            .map {
                it.mapData { res ->
                    res.first { info -> info.id == id }.mockMap()
                }
            }
    }

    override suspend fun reloadCoins(currency: Currency): Resource<Unit, FailureReason> {
        attempts++
        if (attempts == reloadsToWork) {
            dataFlow.value = Resource.Success(data.toImmutableList())
        }
        if (attempts >= reloadsToWork) {
            return Resource.Success(Unit)
        }
        return Resource.Failure(error)
    }

    override suspend fun reloadCoinDetails(id: String): Resource<Unit, FailureReason> {
        attempts++
        if (attempts == reloadsToWork) {
            dataFlow.value = Resource.Success(data.toImmutableList())
        }
        if (attempts >= reloadsToWork) {
            return Resource.Success(Unit)
        }
        return Resource.Failure(error)
    }
}