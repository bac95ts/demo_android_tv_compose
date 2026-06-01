package vn.vtv.vtvgotv.features.player.presentation.composables

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerView(
    playerView: PlayerView,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { playerView },
        modifier = modifier.fillMaxSize()
    )
}
