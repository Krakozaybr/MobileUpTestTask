package com.krakozaybr.domain.model

import kotlinx.collections.immutable.ImmutableList

data class CoinDetails(
    val id: String,
    val imageLink: String,
    val description: String,
    val categories: ImmutableList<String>
)
