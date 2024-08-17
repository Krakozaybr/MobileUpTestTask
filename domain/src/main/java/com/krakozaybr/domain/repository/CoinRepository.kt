package com.krakozaybr.domain.repository

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoins(currency: Currency): Flow<Resource<ImmutableList<CoinInfo>, FailureReason>>

    fun getCoinDetails(id: String): Flow<Resource<CoinDetails, FailureReason>>

}