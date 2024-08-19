package com.krakozaybr.data.repository

import co.touchlab.stately.concurrency.withLock
import com.krakozaybr.data.network.Api
import com.krakozaybr.data.utils.MutableNeverEqualStateFlow
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CurrencyRepository
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.NetworkResource
import com.krakozaybr.domain.resource.Resource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import java.util.concurrent.locks.ReentrantLock

internal class CurrencyRepositoryImpl(
    private val api: Api
) : CurrencyRepository {

    // Use default because error display of currency list is unknown
    private val cache = MutableNeverEqualStateFlow<NetworkResource<ImmutableList<Currency>>?>(null)

    private val lock = ReentrantLock()

    override fun getCurrencies(): Flow<Resource<ImmutableList<Currency>, FailureReason>> {
        return channelFlow {
            if (cache.value == null) lock.withLock {
                if (cache.value != null) return@withLock
                reloadCurrencies()
            }
            cache.filterNotNull().collectLatest(::send)
        }
    }

    override suspend fun reloadCurrencies(): Resource<Unit, FailureReason> {
        return when (val it = api.supportedCurrencies()) {
            is Resource.Success -> {
                val list = it.data
                if (list.isEmpty()) {
                    // We shouldn`t send empty list
                    Resource.Failure(DataError.Network.PAYLOAD_EMPTY)
                } else {
                    cache.value = Resource.Success(
                        list
                            .map { name -> Currency(name.uppercase()) }
                            .toImmutableList()
                    )
                    Resource.Success(Unit)
                }
            }
            is Resource.Failure -> {
                Resource.Failure(it.error)
            }
        }
    }

}