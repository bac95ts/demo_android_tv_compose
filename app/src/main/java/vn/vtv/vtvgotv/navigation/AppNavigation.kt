package vn.vtv.vtvgotv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import vn.vtv.vtvgotv.ui.main.MainScreen
import vn.vtv.vtvgotv.features.player.presentation.PlayerScreen
import vn.vtv.vtvgotv.features.search.presentation.SearchScreen
import vn.vtv.vtvgotv.features.auth.presentation.AccountManagementScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Screen.Main.route) {
            composable(Screen.Main.route) {
                MainScreen()
            }

            composable(Screen.Search.route) {
                SearchScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Screen.Account.route) {
                AccountManagementScreen(
                    onLoginSuccess = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            composable(
                route = Screen.Player.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                PlayerScreen(id = id)
            }
        }
    }
}
