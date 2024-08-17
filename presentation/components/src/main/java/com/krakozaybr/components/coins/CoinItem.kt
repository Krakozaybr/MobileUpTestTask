package com.krakozaybr.components.coins

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.krakozaybr.components.R
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.components.theme.Roboto
import com.krakozaybr.components.utils.englishFormat
import com.krakozaybr.components.utils.symbol
import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import kotlin.math.absoluteValue

@Composable
fun CoinItem(
    currency: Currency,
    coin: CoinInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoinImage(
            imageLink = coin.imageLink,
            contentDescription = coin.name,
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row {
                CoinName(name = coin.name)
                Spacer(modifier = Modifier.weight(1f))
                CoinPrice(currency = currency, price = coin.price)
            }
            Row {
                CoinSymbol(symbol = coin.symbol)
                Spacer(modifier = Modifier.weight(1f))
                CoinPriceChange(change = coin.priceChangePercentage)
            }
        }
    }
}

@Composable
private fun CoinName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = TextStyle(
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        color = AppTheme.extraColors.coinHead,
        modifier = modifier
    )
}

@Composable
private fun CoinPrice(
    currency: Currency,
    price: Float,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${currency.symbol} ${price.englishFormat()}",
        style = TextStyle(
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ),
        color = AppTheme.extraColors.coinHead,
        modifier = modifier
    )
}

@Composable
private fun CoinSymbol(
    symbol: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = symbol,
        style = TextStyle(
            fontFamily = Roboto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        color = AppTheme.extraColors.coinSymbol,
        modifier = modifier
    )
}

@Composable
private fun CoinPriceChange(
    change: Float,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(
            id = if (change < 0) R.string.price_change_up else R.string.price_change_down,
            change.absoluteValue.englishFormat()
        ),
        style = TextStyle(
            fontFamily = Roboto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        color = if (change < 0) AppTheme.extraColors.coinError else AppTheme.extraColors.coinSuccess,
        modifier = modifier
    )
}

@Composable
private fun CoinImage(
    imageLink: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier.aspectRatio(1f),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageLink)
            .crossfade(true)
            .placeholder(R.drawable.placeholder)
            .build(),
        contentDescription = contentDescription
    )
}
