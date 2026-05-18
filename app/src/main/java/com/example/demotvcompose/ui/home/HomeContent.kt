package com.example.demotvcompose.ui.home

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
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import androidx.tv.material3.Surface
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import com.example.demotvcompose.ui.home.viewmodel.HomeViewModel

import androidx.tv.material3.MaterialTheme
import com.example.demotvcompose.ui.theme.VTVRed
import com.example.demotvcompose.ui.theme.VTVGreen

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val launcherItems by viewModel.launcherItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
            val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)
            LazyRow(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 100.dp), // Center padding to show adjacent items
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(Int.MAX_VALUE) { i ->
                    val index = i % launcherItems.size
                    val item = launcherItems[index]
                    
                    var isFocused by remember { mutableStateOf(false) }
                    
                    Surface(
                        onClick = {},
                        modifier = Modifier
                            .width(if (isFocused) 550.dp else 450.dp)
                            .height(if (isFocused) 300.dp else 220.dp)
                            .onFocusChanged { isFocused = it.isFocused },
                        shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(16.dp)),
                        colors = ClickableSurfaceDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            pressedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = ClickableSurfaceDefaults.border(
                            focusedBorder = androidx.tv.material3.Border(androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.border), shape = RoundedCornerShape(16.dp))
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
