package com.example.demotvcompose.features.home.data.remote

import com.example.demotvcompose.features.home.data.remote.api.HomeApiService
import com.example.demotvcompose.features.home.data.remote.model.response.LauncherResponseDto

class HomeRemoteImpl(private val homeApiService: HomeApiService) : HomeRemote {
    override suspend fun getLauncherHomeRemote(): LauncherResponseDto {
        return homeApiService.getLauncherHome()
    }
}
