package com.krakozaybr.domain.repository

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.SimpleResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencies(): Flow<SimpleResource<ImmutableList<Currency>>>

    suspend fun reloadCurrencies(): SimpleResource<Unit>

}