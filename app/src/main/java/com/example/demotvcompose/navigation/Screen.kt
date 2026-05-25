package com.example.demotvcompose.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * Screen Route definitions for the application
 */
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Search : Screen("search")
    object Account : Screen("account")
    object Player : Screen("player/{id}") {
        fun createRoute(id: String) = "player/$id"
    }
}

/**
 * Global CompositionLocal to avoid prop drilling NavController
 */
val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided! Make sure to wrap your hierarchy inside CompositionLocalProvider.")
}
