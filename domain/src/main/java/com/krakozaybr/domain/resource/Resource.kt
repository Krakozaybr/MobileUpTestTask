package com.krakozaybr.domain.resource

sealed interface Resource<out D, out E: FailureReason> {
    data class Success<out D, out E: FailureReason>(val data: D): Resource<D, E>
    data class Failure<out D, out E: FailureReason>(val error: E): Resource<D, E>
}
