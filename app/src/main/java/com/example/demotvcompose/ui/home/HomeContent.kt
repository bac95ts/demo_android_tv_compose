package com.example.demotvcompose.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*
import com.example.demotvcompose.model.LauncherItemModel
import com.example.demotvcompose.theme.DemoTVComposeTheme
import com.example.demotvcompose.ui.home.components.BottomNavigationHint
import com.example.demotvcompose.ui.home.components.CarouselSection
import com.example.demotvcompose.ui.home.components.ChannelSection
import com.example.demotvcompose.ui.home.components.EmptyPageSection
import com.example.demotvcompose.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val carouselSpacing = 16.dp

    // Fixed size per user request
    val carouselItemWidth = 450.dp
    val carouselItemHeight = 220.dp
    // Compute padding to perfectly center the focused item
    val horizontalPadding = (screenWidth - carouselItemWidth) / 2

    var currentPage by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedContent(
            targetState = currentPage,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> -height } + fadeOut())
                } else {
                    (slideInVertically { height -> -height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> height } + fadeOut())
                }
            },
            label = "PageTransition",
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                    CarouselSection(
                        launcherItems = launcherItems,
                        isLoading = isLoading,
                        carouselItemWidth = carouselItemWidth,
                        carouselItemHeight = carouselItemHeight,
                        carouselSpacing = carouselSpacing,
                        horizontalPadding = horizontalPadding,
                        onItemClick = onItemClick,
                        onRequestOpenDrawer = onRequestOpenDrawer
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    // Channel List
                    ChannelSection(
                        onNavigateDown = { currentPage = 1 }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Bottom Hint: "Kéo xuống để khám phá thêm"
                    BottomNavigationHint(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                // Page Below (Empty UI)
                EmptyPageSection(
                    onNavigateUp = { currentPage = 0 }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    DemoTVComposeTheme {
        HomeContentScreen(
            launcherItems = listOf(
                LauncherItemModel("1", "VTV1 Live", "https://picsum.photos/450/220"),
                LauncherItemModel("2", "VTV2 HD", "https://picsum.photos/450/220"),
                LauncherItemModel("3", "VTV3 Live", "https://picsum.photos/450/220")
            ),
            isLoading = false
        )
    }
}
