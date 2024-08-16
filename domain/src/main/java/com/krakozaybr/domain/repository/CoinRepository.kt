package com.krakozaybr.domain.repository

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList

interface CoinRepository {

    suspend fun getCoins(): Resource<ImmutableList<CoinInfo>, FailureReason>

    suspend fun getCoinDetails(): Resource<CoinDetails, FailureReason>

}