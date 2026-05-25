package vn.vtv.vtvgotv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.vtv.vtvgotv.ui.main.MainScreen
import vn.vtv.vtvgotv.features.auth.presentation.navigation.authGraph
import vn.vtv.vtvgotv.features.player.presentation.navigation.playerGraph
import vn.vtv.vtvgotv.features.search.presentation.navigation.searchGraph

/**
 * Main application navigation graph.
 * Delegates actual screen routing to each modular feature graph.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Screen.Main.route) {
            // Main Dashboard Container
            composable(Screen.Main.route) {
                MainScreen()
            }

            // Feature sub-graphs registered modularly
            authGraph(navController)
            searchGraph(navController)
            playerGraph(navController)
        }
    }
}
