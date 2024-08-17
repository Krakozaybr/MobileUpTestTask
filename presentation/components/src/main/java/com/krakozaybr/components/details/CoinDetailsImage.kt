package com.krakozaybr.components.details

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.krakozaybr.components.R

@Composable
fun CoinDetailsImage(
    imageLink: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageLink)
            .crossfade(true)
            .placeholder(R.drawable.placeholder)
            .build(),
        modifier = modifier.aspectRatio(1f),
        contentDescription = contentDescription
    )
}