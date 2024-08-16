package com.krakozaybr.domain.repository

import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList

interface CurrencyRepository {

    suspend fun getCurrencies(): Resource<ImmutableList<Currency>, FailureReason>

}