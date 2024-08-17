package com.krakozaybr.components.toolbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
fun GoBackToolbarIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.clickable(onClick = onClick),
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = stringResource(id = R.string.go_back)
    )
}

@Preview
@Composable
private fun GoBackToolbarIconPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            GoBackToolbarIcon(
                onClick = {},
                modifier = Modifier
            )
        }
    }
}
