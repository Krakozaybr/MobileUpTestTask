package com.krakozaybr.domain.resource

fun <D1, D2, E1 : E2, E2 : FailureReason> Resource<D1, E1>.mapIfSuccess(
    convert: Resource.Success<D1, E1>.() -> Resource<D2, E2>
): Resource<D2, E2> = when (this) {
    is Resource.Failure -> Resource.Failure(error)
    is Resource.Success -> convert(this)
}

fun <D1, D2, E1 : E2, E2 : FailureReason> Resource<D1, E1>.mapData(
    convert: (D1) -> D2
): Resource<D2, E2> = when (this) {
    is Resource.Failure -> Resource.Failure(error)
    is Resource.Success -> Resource.Success(convert(data))
}

inline fun <D, E : FailureReason> Resource<D, E>.onFailure(action: (E) -> Unit): Resource<D, E> {
    if (this is Resource.Failure) action(error)
    return this
}

inline fun <D, E : FailureReason> Resource<D, E>.onSuccess(action: (D) -> Unit): Resource<D, E> {
    if (this is Resource.Success) action(data)
    return this
}
