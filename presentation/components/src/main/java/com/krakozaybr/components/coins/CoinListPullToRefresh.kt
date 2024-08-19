package com.krakozaybr.components.coins

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.R
import com.krakozaybr.components.utils.animatePlacement
import kotlinx.coroutines.flow.distinctUntilChanged

private const val TurnoverTimeMillis = 3000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListPullToRefresh(
    isRefreshing: Boolean,
    state: PullToRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(10.dp)
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = modifier
            .onSizeChanged {
                size = it
            }
            .offset {
                IntOffset(
                    x = 0,
                    y = state.verticalOffset.toInt() - size.height
                )
            }
            .animatePlacement()
            .shadow(2.dp, CircleShape)
            .background(Color.White, CircleShape)
            .clip(CircleShape)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {

        val rotating = rememberInfiniteTransition(
            label = "CoinListPullToRefresh infinite transition"
        )

        val angle = if (state.isRefreshing) {
            rotating.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = InfiniteRepeatableSpec(tween(durationMillis = TurnoverTimeMillis, easing = LinearEasing)),
                label = ""
            ).value
        } else {
            0f
        }

        Icon(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = state.progress * 360f + angle
                },
            painter = painterResource(id = R.drawable.pull_to_refresh_ic),
            contentDescription = null
        )
    }

    val isRefreshingValue by rememberUpdatedState(newValue = isRefreshing)

    LaunchedEffect(state) {
        snapshotFlow { state.isRefreshing }.distinctUntilChanged().collect {
            if (it && !isRefreshingValue) {
                onRefresh()
            }
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing && !state.isRefreshing) {
            state.startRefresh()
        } else if (!isRefreshing && state.isRefreshing) {
            state.endRefresh()
        }
    }

}