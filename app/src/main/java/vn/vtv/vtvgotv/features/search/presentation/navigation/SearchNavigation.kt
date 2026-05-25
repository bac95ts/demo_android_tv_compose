package vn.vtv.vtvgotv.features.search.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import vn.vtv.vtvgotv.features.search.presentation.SearchScreen
import vn.vtv.vtvgotv.navigation.Screen

/**
 * Extension to register the Search navigation graph.
 * This encapsulates the custom keyboard search flow.
 */
fun NavGraphBuilder.searchGraph(navController: NavController) {
    composable(Screen.Search.route) {
        SearchScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
