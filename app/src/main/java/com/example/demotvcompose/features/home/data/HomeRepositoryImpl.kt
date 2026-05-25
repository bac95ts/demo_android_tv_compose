package com.example.demotvcompose.features.home.data

import com.example.demotvcompose.features.home.data.remote.HomeRemote
import com.example.demotvcompose.features.home.domain.HomeRepository
import com.example.demotvcompose.features.home.domain.model.LauncherItemModel
import com.example.demotvcompose.features.home.domain.model.toModel

class HomeRepositoryImpl(private val homeRemote: HomeRemote) : HomeRepository {
    override suspend fun getLauncherItems(): List<LauncherItemModel> {
        return try {
            val response = homeRemote.getLauncherHomeRemote()
            response.data?.map { dto ->
                dto.toModel()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
