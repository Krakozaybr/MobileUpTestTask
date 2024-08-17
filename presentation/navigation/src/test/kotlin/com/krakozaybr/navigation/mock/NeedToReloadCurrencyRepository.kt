package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NeedToReloadCurrencyRepository(
    private val data: List<Currency>,
    private val reloadsToWork: Int,
    private val error: FailureReason
) : CurrencyRepository {

    private var attempts = 0

    private val dataFlow =
        MutableStateFlow<Resource<ImmutableList<Currency>, FailureReason>>(Resource.Failure(error))

    override fun getCurrencies(): Flow<Resource<ImmutableList<Currency>, FailureReason>> {
        return dataFlow.asStateFlow()
    }

    override suspend fun reloadCurrencies(): Resource<Unit, FailureReason> {
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