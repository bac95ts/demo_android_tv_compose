package com.example.demotvcompose.ui.player

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import kotlinx.coroutines.delay

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.foundation.focusable
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    id: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
//    val hlsUrl = "https://vtvgolive-vtv.vtvdigital.vn/w3OJJm25R1UmEMzzpAI9yg/1779195209/vtvgo/vtv1-manifest.m3u8"
    val hlsUrl = "https://canal.mediaserver.com.co/live/buenisimatv.m3u8"

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(hlsUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    var showOsd by remember { mutableStateOf(true) }
    var playerViewRef by remember { mutableStateOf<PlayerView?>(null) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(4000)
        showOsd = false
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .focusRequester(focusRequester)
            .focusable()
            .onPreviewKeyEvent { keyEvent ->
                if (!showOsd && keyEvent.type == KeyEventType.KeyDown) {
                    if (playerViewRef?.isControllerFullyVisible == false) {
                        playerViewRef?.useController = true
                        playerViewRef?.showController()
                    }
                }
                false
            }
    ) {
        // Video Player
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // Start with false
                    playerViewRef = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Custom OSD Overlay
        AnimatedVisibility(
            visible = showOsd,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xE6222222)) // Dark semi-transparent background
                    .padding(horizontal = 32.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Side: Channel Info
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1",
                            color = Color.White,
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Column {
                            Text(
                                text = "VTV1",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Thời sự",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("16:00", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.width(150.dp).height(2.dp).background(Color.Gray))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("16:15", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    // Right Side: D-Pad Instructions
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // D-Pad visual representation
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color.Black, modifier = Modifier.align(Alignment.TopCenter).padding(4.dp))
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Black, modifier = Modifier.align(Alignment.BottomCenter).padding(4.dp))
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.Black, modifier = Modifier.align(Alignment.CenterStart).padding(4.dp))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Black, modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color.Black, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("OK", color = Color.Black, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(24.dp))
                        
                        // Text instructions
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            InstructionRow("Bấm Lên/Xuống để chuyển kênh")
                            InstructionRow("Bấm OK để mở Danh sách kênh")
                            InstructionRow("Bấm Trái/Phải để Xem lại chương trình")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InstructionRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}
