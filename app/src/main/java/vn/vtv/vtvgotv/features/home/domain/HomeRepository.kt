package vn.vtv.vtvgotv.features.home.domain

import vn.vtv.vtvgotv.features.home.domain.model.LauncherItemModel

/**
 * Domain layer repository interface for Home operations
 */
interface HomeRepository {
    suspend fun getLauncherItems(): List<LauncherItemModel>
}
