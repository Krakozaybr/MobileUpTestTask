package com.krakozaybr.data.network

import com.krakozaybr.domain.resource.NetworkResource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResourceCallAdapter<D : Any>(
    private val successType: Type,
) : CallAdapter<D, Call<NetworkResource<D>>> {

    override fun responseType(): Type {
        return successType
    }

    override fun adapt(call: Call<D>): Call<NetworkResource<D>> {
        return ResourceCall(call)
    }

}