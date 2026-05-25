package com.example.demotvcompose.features.home.domain

import com.example.demotvcompose.features.home.domain.model.LauncherItemModel

/**
 * Domain layer repository interface for Home operations
 */
interface HomeRepository {
    suspend fun getLauncherItems(): List<LauncherItemModel>
}
