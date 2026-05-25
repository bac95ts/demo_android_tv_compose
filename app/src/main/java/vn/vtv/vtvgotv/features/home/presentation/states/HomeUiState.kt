package vn.vtv.vtvgotv.features.home.presentation.states

import vn.vtv.vtvgotv.features.home.domain.model.LauncherItemModel

/**
 * Clean representation of Home Screen UI states
 */
data class HomeUiState(
    val launcherItems: List<LauncherItemModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
