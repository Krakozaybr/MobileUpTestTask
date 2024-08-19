package com.krakozaybr.navigation.mock

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.failure
import com.krakozaybr.domain.resource.success
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
        MutableStateFlow<SimpleResource<ImmutableList<Currency>>>(failure(error))

    override fun getCurrencies(): Flow<SimpleResource<ImmutableList<Currency>>> {
        return dataFlow.asStateFlow()
    }

    override suspend fun reloadCurrencies(): SimpleResource<Unit> {
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