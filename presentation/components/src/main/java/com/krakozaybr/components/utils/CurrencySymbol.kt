package com.krakozaybr.components.utils

import java.util.Currency

val com.krakozaybr.domain.model.Currency.symbol
    get() = try {
        Currency.getInstance(name.uppercase()).symbol
    } catch (e: IllegalArgumentException) {
        "?"
    }
