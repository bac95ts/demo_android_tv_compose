package com.example.demotvcompose.data.api

import com.example.demotvcompose.data.model.LauncherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("cdn/home/public/api/v1/get-launcher/tv-android")
    suspend fun getLauncherHome(): LauncherResponse

}
