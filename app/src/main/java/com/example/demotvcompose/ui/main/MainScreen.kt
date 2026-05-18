package com.example.demotvcompose.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.rememberDrawerState
import com.example.demotvcompose.ui.home.HomeContent
import com.example.demotvcompose.ui.main.components.DrawerMenu
import com.example.demotvcompose.ui.placeholder.PlaceholderScreen

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedMenu by remember { mutableStateOf("Trang chủ") }

    NavigationDrawer(
        drawerState = drawerState,
        drawerContent = { drawerValue ->
            DrawerMenu(
                isClosed = drawerValue == DrawerValue.Closed,
                selectedMenu = selectedMenu,
                onMenuSelected = { selectedMenu = it }
            )
        }
    ) {
        // Main Content based on selectedMenu
        when (selectedMenu) {
            "Trang chủ" -> HomeContent(modifier = Modifier.fillMaxSize())
            else -> PlaceholderScreen(title = selectedMenu, modifier = Modifier.fillMaxSize())
        }
    }
}
