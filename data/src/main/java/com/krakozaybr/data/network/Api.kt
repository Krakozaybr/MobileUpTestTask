package com.krakozaybr.data.network

import com.krakozaybr.data.dtos.CoinDetailsDTO
import com.krakozaybr.data.dtos.CoinInfoDTO
import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.domain.resource.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface Api {

    @GET("simple/supported_vs_currencies")
    fun supportedCurrencies(): Resource<List<String>, DataError.Network>

    @GET("coins/markets")
    fun getList(@Query("vs_currency") currency: String): Resource<List<CoinInfoDTO>, DataError.Network>

    @GET("coins/{id}")
    fun getDetails(@Path("id") id: String): Resource<CoinDetailsDTO, DataError.Network>

}