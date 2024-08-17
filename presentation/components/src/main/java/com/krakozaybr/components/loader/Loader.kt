package com.krakozaybr.components.loader

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.theme.AppTheme

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    thickness: Dp = size / 11f,
    animationSpec: InfiniteRepeatableSpec<Float> = InfiniteRepeatableSpec(
        tween(
            durationMillis = 1200
        )
    ),
    color: Color = AppTheme.extraColors.loaderColor
) {
    val transition = rememberInfiniteTransition(
        label = "Loader transition"
    )

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = animationSpec,
        label = "Loader angle animation"
    )

    Canvas(
        modifier = Modifier
            .size(size)
            .then(modifier)
    ) {
        drawArc(
            color = color,
            startAngle = rotation,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = thickness.toPx())
        )
    }
}

@Preview
@Composable
private fun LoaderPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Loader(
                modifier = Modifier.size(100.dp)
            )
        }
    }
}