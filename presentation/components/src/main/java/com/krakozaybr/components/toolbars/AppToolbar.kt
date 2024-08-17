package com.krakozaybr.components.toolbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.theme.AppTheme

@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(20.dp),
    shadowElevation: Dp = AppTheme.sizes.toolbarShadowElevation,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        content = {
            Box(
                content = content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            )
        },
        shadowElevation = shadowElevation,
        modifier = modifier
    )
}
