package com.krakozaybr.components.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.R
import com.krakozaybr.components.theme.AppTheme

@Composable
fun ErrorBanner(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = modifier.fillMaxWidth(0.6f),
            painter = painterResource(id = R.drawable.error_logo),
            contentDescription = stringResource(id = R.string.error)
        )

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = stringResource(id = R.string.error_text),
            textAlign = TextAlign.Center,
            style = AppTheme.extraTypography.mediumText
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(AppTheme.extraColors.main)
                .clickable(onClick = onRetryClick)
                .padding(vertical = 13.dp, horizontal = 33.dp)
        ) {
            Text(
                text = stringResource(id = R.string.caps_try),
                color = AppTheme.extraColors.onMain,
                style = AppTheme.extraTypography.capsButton
            )
        }
    }
}

@Preview
@Composable
private fun ErrorBannerPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ErrorBanner(
                onRetryClick = {},
                modifier = Modifier
            )
        }
    }
}
