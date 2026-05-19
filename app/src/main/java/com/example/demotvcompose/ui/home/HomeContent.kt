package com.example.demotvcompose.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.focus.onFocusChanged
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import androidx.tv.material3.Surface
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import com.example.demotvcompose.ui.home.viewmodel.HomeViewModel

import androidx.tv.material3.MaterialTheme

import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.FocusDirection

import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onRequestOpenDrawer: () -> Unit = {}
) {
    val launcherItems by viewModel.launcherItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val carouselSpacing = 16.dp
    
    // Fixed size per user request
    val carouselItemWidth = 450.dp
    val carouselItemHeight = 220.dp
    // Compute padding to perfectly center the focused item
    val horizontalPadding = (screenWidth - carouselItemWidth) / 2

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
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
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Carousel from API
        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                Text("Loading...", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
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
                } catch (e: Exception) {}
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
                modifier = Modifier
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
                            .then(if (i == centerIndex) Modifier.focusRequester(initialFocusRequester) else Modifier)
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
                        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
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
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Channel List (Keep as is)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                Surface(
                    onClick = {},
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),
                    shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp)),
                    colors = ClickableSurfaceDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        pressedContainerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "VTV ${index + 1}", 
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
