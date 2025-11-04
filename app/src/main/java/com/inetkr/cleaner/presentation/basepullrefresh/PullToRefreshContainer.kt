package com.inetkr.cleaner.presentation.basepullrefresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshContainer(
    content: @Composable () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBox(
        modifier = Modifier.statusBarsPadding(),
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                delay(2000)
                isRefreshing = false
            }
        },
        indicator = {
            MyCustomIndicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        content()
    }
}


@Composable
fun MyCustomIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .pullToRefresh(
                state = state,
                isRefreshing = isRefreshing,
                threshold = PositionalThreshold,
                onRefresh = {

                }
            )
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = 300),
            modifier = Modifier.align(Alignment.Center)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 3.dp,
                )
            } else {
                val distanceFraction = state.distanceFraction.coerceIn(0f, 1f)
                Icon(
                    imageVector = Icons.Filled.CloudDownload,
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .size((18.dp * distanceFraction).coerceAtLeast(16.dp))
                        .graphicsLayer {
                            this.alpha = distanceFraction
                            this.scaleX = distanceFraction.coerceIn(0.5f, 1f)
                            this.scaleY = distanceFraction.coerceIn(0.5f, 1f)
                        }
                )
            }
        }
    }
}