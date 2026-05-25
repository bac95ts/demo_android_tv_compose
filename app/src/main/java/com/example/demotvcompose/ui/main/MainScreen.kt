package com.example.demotvcompose.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.rememberDrawerState
import com.example.demotvcompose.navigation.LocalNavController
import com.example.demotvcompose.navigation.Screen
import com.example.demotvcompose.features.home.presentation.HomeScreen
import com.example.demotvcompose.ui.main.components.DrawerMenu
import com.example.demotvcompose.core.ui_kit.placeholder.PlaceholderScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedMenu by remember { mutableStateOf("Trang chủ") }
    val coroutineScope = rememberCoroutineScope()

    NavigationDrawer(
        drawerState = drawerState,
        drawerContent = { drawerValue ->
            DrawerMenu(
                isClosed = drawerValue == DrawerValue.Closed,
                selectedMenu = selectedMenu,
                onMenuSelected = {
                    selectedMenu = it
                }
            )
        }
    ) {
        // Main Content based on selectedMenu
        when (selectedMenu) {
            "Trang chủ" -> HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onItemClick = { id ->
                    navController.navigate(Screen.Player.createRoute(id))
                },
                onRequestOpenDrawer = {
                    coroutineScope.launch { drawerState.setValue(DrawerValue.Open) }
                }
            )

            "Tìm kiếm" -> navController.navigate(Screen.Search.route)
            "Quản lý tài khoản" -> navController.navigate(Screen.Account.route)
            else -> PlaceholderScreen(title = selectedMenu, modifier = Modifier.fillMaxSize())
        }
    }
}
