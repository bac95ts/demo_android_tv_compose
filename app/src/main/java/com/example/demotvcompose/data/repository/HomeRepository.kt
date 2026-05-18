package com.example.demotvcompose.data.repository

import com.example.demotvcompose.data.api.ApiService
import com.example.demotvcompose.model.LauncherItemModel

class HomeRepository(private val apiService: ApiService) {
    suspend fun getLauncherItems(): List<LauncherItemModel> {
        return try {
            val dtoData = apiService.getLauncherHome().data ?: emptyList()
            dtoData.map { LauncherItemModel.fromDto(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
