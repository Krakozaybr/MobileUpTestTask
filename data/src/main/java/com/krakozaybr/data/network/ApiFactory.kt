package com.krakozaybr.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal object ApiFactory {

    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    private val client = OkHttpClient.Builder()
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(ResourceCallAdapterFactory())
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json; charset=UTF8")))
        .build()

    val apiService: Api get() = retrofit.create(Api::class.java)

}
