package vn.vtv.vtvgotv.features.player.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import vn.vtv.vtvgotv.features.player.presentation.PlayerScreen
import vn.vtv.vtvgotv.navigation.Screen

/**
 * Extension to register the Player navigation graph.
 * This encapsulates the media player screens and handles route arguments.
 */
fun NavGraphBuilder.playerGraph(navController: NavController) {
    composable(
        route = Screen.Player.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        PlayerScreen(id = id)
    }
}
