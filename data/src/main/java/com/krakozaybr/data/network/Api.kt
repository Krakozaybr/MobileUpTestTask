package com.krakozaybr.data.network

import com.krakozaybr.data.dtos.CoinDetailsDTO
import com.krakozaybr.data.dtos.CoinInfoDTO
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.domain.resource.NetworkResource
import com.krakozaybr.domain.resource.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface Api {

    @GET("simple/supported_vs_currencies")
    suspend fun supportedCurrencies(): NetworkResource<List<String>>

    @GET("coins/markets")
    suspend fun getList(@Query("vs_currency") currency: String): NetworkResource<List<CoinInfoDTO>>

    @GET("coins/{id}")
    suspend fun getDetails(@Path("id") id: String): NetworkResource<CoinDetailsDTO>

}