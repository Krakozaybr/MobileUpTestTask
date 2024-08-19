package com.krakozaybr.components.coins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.krakozaybr.components.R
import com.krakozaybr.components.theme.AppTheme
import kotlinx.coroutines.delay

private const val LIVE_TIME_MILLIS = 3000L

@Composable
fun RefreshFailedMessage(
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Snackbar (
        modifier = modifier,
        containerColor = AppTheme.colorScheme.error,
        contentColor = AppTheme.colorScheme.onError
    ) {
        Text(text = stringResource(id = R.string.snackbar_error_text))
    }

    LaunchedEffect (Unit) {
        delay(LIVE_TIME_MILLIS)
        onCancel()
    }
}

@Preview
@Composable
fun RefreshFailedMessagePreview() {

    AppTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center) {
            RefreshFailedMessage({})
        }
    }

}