package com.krakozaybr.domain.repository

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.SimpleResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoins(currency: Currency): Flow<SimpleResource<ImmutableList<CoinInfo>>>

    fun getCoinDetails(id: String): Flow<SimpleResource<CoinDetails>>

    suspend fun reloadCoins(currency: Currency): SimpleResource<Unit>

    suspend fun reloadCoinDetails(id: String): SimpleResource<Unit>

}