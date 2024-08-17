package com.krakozaybr.components.toolbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.theme.AppTheme
import extensions.dropShadowOutOfBounds

@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(20.dp),
    shadowElevation: Dp = AppTheme.sizes.toolbarShadowSize,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        content = content,
        modifier = modifier
            .fillMaxWidth()
            .dropShadowOutOfBounds(
                spread = shadowElevation,
                shape = RectangleShape,
            )
            .background(AppTheme.colorScheme.surface)
            .padding(paddingValues)
    )
}
