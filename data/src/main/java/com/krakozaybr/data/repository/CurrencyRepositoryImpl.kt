package com.krakozaybr.data.repository

import com.krakozaybr.data.network.Api
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.onSuccess
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

internal class CurrencyRepositoryImpl(
    private val api: Api
) : CurrencyRepository {

    // Use default because error display of currency list is unknown
    private val cache = MutableStateFlow<ImmutableList<Currency>>(default)

    override fun getCurrencies(): Flow<Resource<ImmutableList<Currency>, FailureReason>> {
        return flow {
            if (cache.value == default) reloadCurrencies()

            cache.collectLatest { list ->

                // make RUB and USD first ones
                val res = mutableListOf<Currency>()
                res.addAll(default)
                res.addAll(list.filter { it !in default })

                emit(
                    Resource.Success(
                        res.toImmutableList()
                    )
                )
            }
        }
    }

    override suspend fun reloadCurrencies(): Resource<Unit, FailureReason> {
        api.supportedCurrencies().onSuccess {
            cache.value = it.map { name -> Currency(name) }.toImmutableList()
        }
        // Success because if catch error from network, we show default
        return Resource.Success(Unit)
    }

    companion object {

        private val default = persistentListOf(
            Currency("RUB"),
            Currency("USD"),
        )

    }
}