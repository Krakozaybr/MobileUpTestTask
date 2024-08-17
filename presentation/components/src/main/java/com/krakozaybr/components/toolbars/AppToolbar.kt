package com.krakozaybr.components.toolbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import com.krakozaybr.components.theme.AppTheme
import extensions.dropShadowOutOfBounds

@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
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
            .background(AppTheme.colorScheme.background)
    )
}
