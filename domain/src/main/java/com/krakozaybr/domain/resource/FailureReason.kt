package com.krakozaybr.domain.resource

sealed interface FailureReason

data object DataError : FailureReason
