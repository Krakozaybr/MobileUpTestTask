package com.krakozaybr.data.network

import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.domain.resource.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResourceCallAdapter<D : Any>(
    private val successType: Type,
) : CallAdapter<D, Call<Resource<D, DataError.Network>>> {

    override fun responseType(): Type {
        return successType
    }

    override fun adapt(call: Call<D>): Call<Resource<D, DataError.Network>> {
        return ResourceCall(call)
    }

}