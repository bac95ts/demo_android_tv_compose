package com.example.demotvcompose.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*
import coil.compose.AsyncImage
import com.example.demotvcompose.model.LauncherItemModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CarouselSection(
    launcherItems: List<LauncherItemModel>,
    isLoading: Boolean,
    carouselItemWidth: Dp,
    carouselItemHeight: Dp,
    carouselSpacing: Dp,
    horizontalPadding: Dp,
    onItemClick: (String) -> Unit,
    onRequestOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Loading...",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
    } else if (launcherItems.isNotEmpty()) {
        val centerIndex = Int.MAX_VALUE / 2
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)
        val focusManager = LocalFocusManager.current
        var isCarouselFocused by remember { mutableStateOf(false) }
        val initialFocusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(100)
            try {
                initialFocusRequester.requestFocus()
            } catch (e: Exception) {
            }
        }

        LaunchedEffect(listState, isCarouselFocused) {
            while (true) {
                delay(4000)
                if (isCarouselFocused) {
                    focusManager.moveFocus(FocusDirection.Right)
                } else if (!listState.isScrollInProgress) {
                    listState.animateScrollToItem(listState.firstVisibleItemIndex + 1)
                }
            }
        }

        LazyRow(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { isCarouselFocused = it.hasFocus },
            horizontalArrangement = Arrangement.spacedBy(carouselSpacing),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(Int.MAX_VALUE) { i ->
                val index = i % launcherItems.size
                val item = launcherItems[index]

                var isFocused by remember { mutableStateOf(false) }

                Surface(
                    onClick = { onItemClick(item.id) },
                    modifier = Modifier
                        .width(carouselItemWidth)
                        .height(carouselItemHeight)
                        .then(
                            if (i == centerIndex) Modifier.focusRequester(
                                initialFocusRequester
                            ) else Modifier
                        )
                        .onFocusChanged { isFocused = it.isFocused }
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.DirectionUp && keyEvent.type == KeyEventType.KeyDown) {
                                onRequestOpenDrawer()
                                true
                            } else {
                                false
                            }
                        },
                    shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(16.dp)),
                    colors = ClickableSurfaceDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        pressedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                    border = ClickableSurfaceDefaults.border(
                        focusedBorder = Border(
                            BorderStroke(
                                3.dp,
                                MaterialTheme.colorScheme.border
                            ), shape = RoundedCornerShape(16.dp)
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        AsyncImage(
                            model = item.image,
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (isFocused) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.6f))
                            )
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
