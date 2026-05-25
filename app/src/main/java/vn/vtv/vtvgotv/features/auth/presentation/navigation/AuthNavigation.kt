package vn.vtv.vtvgotv.features.auth.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import vn.vtv.vtvgotv.features.auth.presentation.AccountManagementScreen
import vn.vtv.vtvgotv.navigation.Screen

/**
 * Extension to register the Auth navigation graph.
 * This encapsulates all screens related to authentication.
 */
fun NavGraphBuilder.authGraph(navController: NavController) {
    composable(Screen.Account.route) {
        AccountManagementScreen(
            onLoginSuccess = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
