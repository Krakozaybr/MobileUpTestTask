package com.krakozaybr.components.toolbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.R
import com.krakozaybr.components.theme.AppTheme

@Composable
fun CoinListToolbar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(AppTheme.sizes.screenPadding),
    spacing: Dp = 0.dp,
    bottomContent: @Composable () -> Unit,
) {
    AppToolbar(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
        ) {
            ToolbarText(
                text = stringResource(id = R.string.coin_list),
                modifier = Modifier.padding(paddingValues)
            )
            bottomContent()
        }
    }
}

@Preview
@Composable
private fun CoinListToolbarPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CoinListToolbar {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(21.dp)
                ) {
                    repeat(10) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}
