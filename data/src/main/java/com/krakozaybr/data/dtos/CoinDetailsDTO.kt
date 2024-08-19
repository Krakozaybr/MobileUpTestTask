package com.krakozaybr.data.dtos

import com.krakozaybr.domain.model.CoinDetails
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.Locale

@Serializable
internal data class CoinDetailsDTO(
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val imageLink: ImageHolder,
    @SerialName("description")
    val description: JsonObject,
    @SerialName("categories")
    val categories: List<String>
) {
    fun map() = CoinDetails(
        id = id,
        imageLink = imageLink.large,
        description = description(Locale.getDefault().language)
            ?: description("en")
            ?: "",
        categories = categories.toImmutableList()
    )

    private fun description(locale: String): String? {
        return description[locale]
            ?.jsonPrimitive
            ?.takeIf { it.isString }
            ?.content
            ?.takeIf { it.isNotEmpty() && it.isNotBlank() }
    }
}

@Serializable
internal data class ImageHolder(
    @SerialName("thumb")
    val thumb: String,
    @SerialName("small")
    val small: String,
    @SerialName("large")
    val large: String
)
