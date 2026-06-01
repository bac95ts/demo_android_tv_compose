package vn.vtv.vtvgotv.features.player.presentation

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import org.koin.androidx.compose.koinViewModel
import vn.vtv.vtvgotv.features.player.presentation.composables.PlayerOsdOverlay
import vn.vtv.vtvgotv.features.player.presentation.composables.VideoPlayerView
import vn.vtv.vtvgotv.features.player.presentation.viewmodel.PlayerViewModel

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val hlsUrl = "https://canal.mediaserver.com.co/live/buenisimatv.m3u8"
    val adTagUrl = "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&correlator="
    val skipTime = 5

    // We create and remember a single PlayerView instance in Composable
    val playerView = remember {
        PlayerView(context).apply {
            useController = false
        }
    }

    // Release player when leaving composition
    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer(playerView)
        }
    }

    val showOsd by viewModel.showOsd.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.initializePlayer(context, playerView, hlsUrl, adTagUrl)
        focusRequester.requestFocus()
        viewModel.startOsdTimer()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .focusRequester(focusRequester)
            .focusable()
            .onPreviewKeyEvent { keyEvent ->
                if (!showOsd && keyEvent.type == KeyEventType.KeyDown) {
                    if (!playerView.isControllerFullyVisible) {
                        playerView.useController = true
                        playerView.showController()
                    }
                    viewModel.showOsdBriefly()
                }
                false
            }
    ) {
        // Video Player View Wrapper
        VideoPlayerView(playerView = playerView)

        // Custom OSD Overlay Component
        PlayerOsdOverlay(
            visible = showOsd,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
