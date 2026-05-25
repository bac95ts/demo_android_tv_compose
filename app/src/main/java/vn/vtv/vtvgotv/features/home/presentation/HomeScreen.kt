package vn.vtv.vtvgotv.features.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*
import coil.compose.AsyncImage
import vn.vtv.vtvgotv.core.theme.DemoTVComposeTheme
import vn.vtv.vtvgotv.features.home.domain.model.LauncherItemModel
import vn.vtv.vtvgotv.features.home.presentation.composables.BottomNavigationHint
import vn.vtv.vtvgotv.features.home.presentation.composables.CarouselSection
import vn.vtv.vtvgotv.features.home.presentation.composables.ChannelSection
import vn.vtv.vtvgotv.features.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onRequestOpenDrawer: () -> Unit = {}
) {
    val launcherItems by viewModel.launcherItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    HomeContentScreen(
        launcherItems = launcherItems,
        isLoading = isLoading,
        modifier = modifier,
        onItemClick = onItemClick,
        onRequestOpenDrawer = onRequestOpenDrawer
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeContentScreen(
    launcherItems: List<LauncherItemModel>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
    onRequestOpenDrawer: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    // Determine if we are at the very top of the scroll list
    val isScrolledToTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            // Item 0: Takes exactly 100% of screen height
            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = 24.dp, bottom = 16.dp)
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "10:11\nT2 - 18/05",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Carousel Section
                    CarouselSection(
                        launcherItems = launcherItems,
                        isLoading = isLoading,
                        carouselItemWidth = 450.dp,
                        carouselItemHeight = 220.dp,
                        carouselSpacing = 16.dp,
                        horizontalPadding = 0.dp,
                        onItemClick = onItemClick,
                        onRequestOpenDrawer = onRequestOpenDrawer
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Channel List Section
                    ChannelSection()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Bottom Navigation Hint (only visible when scrolled at the top)
                    AnimatedVisibility(
                        visible = isScrolledToTop,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        BottomNavigationHint()
                    }
                }
            }

            // Gap spacer
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Item 1: Kênh DVR
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "Kênh DVR",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(2) { index ->
                            val title = if (index == 0) "VTV2 DVR" else "VTV3 DVR"
                            val imageUrl = "https://picsum.photos/320/180?random=${index + 1}"
                            var isFocused by remember { mutableStateOf(false) }

                            Surface(
                                onClick = {},
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(124.dp)
                                    .onFocusChanged { isFocused = it.isFocused },
                                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                                colors = ClickableSurfaceDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    pressedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                                border = ClickableSurfaceDefaults.border(
                                    focusedBorder = Border(
                                        BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.border
                                        ), shape = RoundedCornerShape(8.dp)
                                    )
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    if (isFocused) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.5f))
                                        )
                                    }
                                    Text(
                                        text = title,
                                        color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.8f
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            val items = listOf(
                Pair("Vietnam Today", "https://picsum.photos/320/180?random=3"),
                Pair("VTV10", "https://picsum.photos/320/180?random=4"),
                Pair(
                    "Loạt đá luân lưu cân não",
                    "https://picsum.photos/320/180?random=5"
                ),
                Pair(
                    "Loạt đá luân lưu cân não 2",
                    "https://picsum.photos/320/180?random=6"
                ),
                Pair(
                    "Loạt đá luân lưu cân não 3",
                    "https://picsum.photos/320/180?random=7"
                ),
                Pair(
                    "Loạt đá luân lưu cân não 4",
                    "https://picsum.photos/320/180?random=8"
                )
            )

            // Gap spacer
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Item 2: Phim VTV
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "Phim VTV",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(items.size) { index ->
                            val item = items[index]
                            var isFocused by remember { mutableStateOf(false) }

                            Surface(
                                onClick = {},
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(124.dp)
                                    .onFocusChanged { isFocused = it.isFocused },
                                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                                colors = ClickableSurfaceDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    pressedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                                border = ClickableSurfaceDefaults.border(
                                    focusedBorder = Border(
                                        BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.border
                                        ), shape = RoundedCornerShape(8.dp)
                                    )
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        model = item.second,
                                        contentDescription = item.first,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    if (isFocused) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.5f))
                                        )
                                    }
                                    Text(
                                        text = item.first,
                                        color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.8f
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Gap spacer
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Item 3: Phim VTV
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "Phim VTV",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(items.size) { index ->
                            val item = items[index]
                            var isFocused by remember { mutableStateOf(false) }

                            Surface(
                                onClick = {},
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(124.dp)
                                    .onFocusChanged { isFocused = it.isFocused },
                                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                                colors = ClickableSurfaceDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    pressedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                                border = ClickableSurfaceDefaults.border(
                                    focusedBorder = Border(
                                        BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.border
                                        ), shape = RoundedCornerShape(8.dp)
                                    )
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        model = item.second,
                                        contentDescription = item.first,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    if (isFocused) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.5f))
                                        )
                                    }
                                    Text(
                                        text = item.first,
                                        color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.8f
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Gap spacer
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Item 4: Phim VTV
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "Phim VTV",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(items.size) { index ->
                            val item = items[index]
                            var isFocused by remember { mutableStateOf(false) }

                            Surface(
                                onClick = {},
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(124.dp)
                                    .onFocusChanged { isFocused = it.isFocused },
                                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                                colors = ClickableSurfaceDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    pressedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                                border = ClickableSurfaceDefaults.border(
                                    focusedBorder = Border(
                                        BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.border
                                        ), shape = RoundedCornerShape(8.dp)
                                    )
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        model = item.second,
                                        contentDescription = item.first,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    if (isFocused) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.5f))
                                        )
                                    }
                                    Text(
                                        text = item.first,
                                        color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.8f
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // End spacing spacer
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    DemoTVComposeTheme {
        val items = listOf(
            Pair("Vietnam Today", "https://picsum.photos/320/180?random=3"),
            Pair("VTV10", "https://picsum.photos/320/180?random=4"),
            Pair(
                "Loạt đá luân lưu cân não",
                "https://picsum.photos/320/180?random=5"
            ),
            Pair(
                "Loạt đá luân lưu cân não 2",
                "https://picsum.photos/320/180?random=6"
            ),
            Pair(
                "Loạt đá luân lưu cân não 3",
                "https://picsum.photos/320/180?random=7"
            ),
            Pair(
                "Loạt đá luân lưu cân não 4",
                "https://picsum.photos/320/180?random=8"
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Phim VTV",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    var isFocused by remember { mutableStateOf(false) }

                    Surface(
                        onClick = {},
                        modifier = Modifier
                            .width(220.dp)
                            .height(124.dp)
                            .onFocusChanged { isFocused = it.isFocused },
                        shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                        colors = ClickableSurfaceDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            pressedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                        border = ClickableSurfaceDefaults.border(
                            focusedBorder = Border(
                                BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.border
                                ), shape = RoundedCornerShape(8.dp)
                            )
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = item.second,
                                contentDescription = item.first,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (isFocused) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.5f))
                                )
                            }
                            Text(
                                text = item.first,
                                color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.8f
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
