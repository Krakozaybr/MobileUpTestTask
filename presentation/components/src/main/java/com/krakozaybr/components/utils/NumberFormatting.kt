package com.krakozaybr.components.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Float.englishFormat(
    maximumFractionDigits: Int = 2
): String {
    val format = DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    format.maximumFractionDigits = maximumFractionDigits
    return format.format(this)
}
