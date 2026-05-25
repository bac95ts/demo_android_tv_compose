package com.example.demotvcompose.features.home.data.remote

import com.example.demotvcompose.features.home.data.remote.model.response.LauncherResponseDto

interface HomeRemote {
    suspend fun getLauncherHomeRemote(): LauncherResponseDto
}
