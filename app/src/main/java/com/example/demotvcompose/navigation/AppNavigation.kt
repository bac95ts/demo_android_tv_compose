package com.example.demotvcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demotvcompose.ui.main.MainScreen
import com.example.demotvcompose.ui.player.PlayerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onNavigateToPlayer = { id ->
                    navController.navigate("player/$id")
                }
            )
        }
        
        composable(
            route = "player/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            PlayerScreen(id = id)
        }
    }
}
