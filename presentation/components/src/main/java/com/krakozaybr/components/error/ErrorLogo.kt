package com.krakozaybr.components.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.krakozaybr.components.R
import com.krakozaybr.components.theme.AppTheme

@Composable
fun ErrorLogo(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.error_logo),
        contentDescription = stringResource(id = R.string.error)
    )
}

@Preview
@Composable
private fun ErrorLogoPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            ErrorLogo()
        }
    }
}
