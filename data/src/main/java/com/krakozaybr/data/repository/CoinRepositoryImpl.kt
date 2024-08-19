package com.krakozaybr.data.repository

import android.util.Log
import com.krakozaybr.data.network.Api
import com.krakozaybr.data.utils.MutableNeverEqualStateFlow
import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.domain.repository.CoinRepository
import com.krakozaybr.domain.resource.FailureReason
import com.krakozaybr.domain.resource.NetworkResource
import com.krakozaybr.domain.resource.Resource
import com.krakozaybr.domain.resource.SimpleResource
import com.krakozaybr.domain.resource.mapData
import com.krakozaybr.domain.resource.onFailure
import com.krakozaybr.domain.resource.onSuccess
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap


// There are similar logics, but I dont think it is crucial
internal class CoinRepositoryImpl(
    private val api: Api,
    // https://developer.android.com/topic/architecture/data-layer#make_an_operation_live_longer_than_the_screen
    private val externalScope: CoroutineScope
) : CoinRepository {

    private val coinListCache =
        ConcurrentHashMap<Currency, MutableStateFlow<NetworkResource<ImmutableList<CoinInfo>>>>()

    private suspend fun Api.loadCoins(currency: Currency): NetworkResource<ImmutableList<CoinInfo>> {
        // Do it in external scope to be invincible for outer cancellations
        // (for example, activity destroy)
        return externalScope.async {
            getList(currency.name).mapData {
                it.map { dto -> dto.map() }.toImmutableList()
            }
        }.await()
    }

    override fun getCoins(currency: Currency): Flow<SimpleResource<ImmutableList<CoinInfo>>> {
        2 + 2
        return channelFlow {
            coinListCache.getOrPut(
                currency,
            ) {
                Log.d("TAG", "getCoins: STARTED LOADING")

                val result = MutableNeverEqualStateFlow(api.loadCoins(currency))

                Log.d("TAG", "getCoins: LOADED")

                coinListCache[currency] = result

                // Clean cache if there is no subscriptions
                externalScope.launch {
                    result.subscriptionCount.collectLatest {
                        if (it > 0) return@collectLatest
                        delay(INACTIVE_LIST_FLOW_LIVE_MILLIS)
                        coinListCache.remove(currency)
                    }
                }

                result
            }.collect(::send)
        }
    }

    override suspend fun reloadCoins(): Map<Currency, SimpleResource<Unit>> {
        return coinListCache.map { (k, v) ->
            // Iterating over all subscriptions and reload them simultaneously

            // Do it in external scope to be invincible for outer cancellations
            // (for example, activity destroy)
            externalScope.async {
                val res = api.loadCoins(k)
                res.onSuccess {
                    // if success just update value
                    v.value = res
                }.onFailure {
                    // if error we should pass old value if it was success
                    // and set new error if not
                    if (v.value is Resource.Failure) {
                        v.value = res
                    }
                }
                // return Pair<Currency, Resource<Unit, DataError.Network>
                k to res.mapData { Unit }
            }
        }
            // Awaiting for all
            .map { it.await() }
            // And creating from results map
            .associate { it }
    }

    private suspend fun Api.loadDetails(id: String): NetworkResource<CoinDetails> {
        // Do it in external scope to be invincible for outer cancellations
        // (for example, activity destroy)
        return externalScope.async {
            getDetails(id).mapData {
                it.map()
            }
        }.await()
    }

    private val detailsCache =
        ConcurrentHashMap<String, MutableStateFlow<NetworkResource<CoinDetails>>>()

    override fun getCoinDetails(id: String): Flow<SimpleResource<CoinDetails>> {
        return channelFlow {

            val result = MutableNeverEqualStateFlow(api.loadDetails(id))

            detailsCache[id] = result

            // Clean cache if there is no subscriptions
            externalScope.launch {
                result.subscriptionCount.collectLatest {
                    if (it > 0) return@collectLatest
                    delay(INACTIVE_DETAILS_FLOW_LIVE_MILLIS)
                    detailsCache.remove(id)
                }
            }

            result.collect(::send)

        }
    }

    override suspend fun reloadCoinDetails(id: String): Resource<Unit, FailureReason> {

        val state = detailsCache[id]?.takeIf { it.subscriptionCount.value > 0 }
            ?: throw RuntimeException("Reload details shouldn`t be called if there is no subscriptions")

        // Do it in external scope to be invincible for outer cancellations
        // (for example, activity destroy)
        return externalScope.async {

            val res = api.loadDetails(id)

            res.onSuccess {
                // if success just update value
                state.value = res
            }.onFailure {
                // if error we should pass old value if it was success
                // and set new error if not
                if (state.value is Resource.Failure) {
                    state.value = res
                }
            }

            res.mapData { Unit }

        }.await()
    }

    companion object {

        // One minute
        private const val INACTIVE_LIST_FLOW_LIVE_MILLIS = 60 * 1000L

        // One minute
        private const val INACTIVE_DETAILS_FLOW_LIVE_MILLIS = 60 * 1000L

    }
}