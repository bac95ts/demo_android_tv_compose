package com.example.demotvcompose.data.repository

import com.example.demotvcompose.data.api.ApiService
import com.example.demotvcompose.data.model.LauncherItem

class HomeRepository(private val apiService: ApiService) {
    suspend fun getLauncherItems(): List<LauncherItem> {
        return try {
            apiService.getLauncherHome().data ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
