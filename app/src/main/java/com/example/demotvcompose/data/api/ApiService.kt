package com.example.demotvcompose.data.api

import com.example.demotvcompose.data.dto.LauncherResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("cdn/home/public/api/v1/get-launcher/tv-android")
    suspend fun getLauncherHome(): LauncherResponseDto
}
