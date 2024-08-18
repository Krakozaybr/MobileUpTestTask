package com.krakozaybr.data.network

import com.krakozaybr.domain.resource.DataError
import com.krakozaybr.domain.resource.Resource
import kotlinx.serialization.SerializationException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class ResourceCall<D>(
    private val delegate: Call<D>
) : Call<Resource<D, DataError.Network>> {
    
    override fun enqueue(callback: Callback<Resource<D, DataError.Network>>) {
        delegate.enqueue(object : Callback<D> {
            override fun onResponse(call: Call<D>, response: Response<D>) {
                callback.onResponse(this@ResourceCall, responseSuccess(response))
            }

            override fun onFailure(call: Call<D>, t: Throwable) {
                callback.onResponse(this@ResourceCall, responseFailure(t))
            }

        })
    }

    override fun execute(): Response<Resource<D, DataError.Network>> {
        return try {
            val resp = delegate.execute()
            responseSuccess(resp)
        } catch (t: Throwable) {
            responseFailure(t)
        }
    }

    private fun responseFailure(
        t: Throwable
    ): Response<Resource<D, DataError.Network>> {
        val error = when (t) {
            is IOException -> DataError.Network.NO_INTERNET
            is SerializationException -> DataError.Network.SERIALIZATION
            else -> DataError.Network.UNKNOWN
        }
        return Response.success(
            Resource.Failure(error)
        )
    }

    private fun responseSuccess(
        response: Response<D>
    ): Response<Resource<D, DataError.Network>> {
        val body = response.body()

        return Response.success(
            if (body == null) {
                Resource.Failure(DataError.Network.PAYLOAD_EMPTY)
            } else if (response.isSuccessful) {
                Resource.Success(body)
            } else {
                Resource.Failure(
                    when (response.code()) {
                        408 -> DataError.Network.REQUEST_TIMEOUT
                        429 -> DataError.Network.REQUEST_TIMEOUT
                        413 -> DataError.Network.PAYLOAD_TOO_LARGE
                        in 500..<600 -> DataError.Network.SERVER_ERROR
                        else -> DataError.Network.UNKNOWN
                    }
                )
            }
        )
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone() = ResourceCall(delegate.clone())
}
