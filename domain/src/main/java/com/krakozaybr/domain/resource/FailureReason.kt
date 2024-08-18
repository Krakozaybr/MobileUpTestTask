package com.krakozaybr.domain.resource

sealed interface FailureReason

sealed interface DataError : FailureReason {

    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        PAYLOAD_EMPTY,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
}
