package vn.vtv.vtvgotv.features.home.data.remote.api

import vn.vtv.vtvgotv.features.home.data.remote.model.response.LauncherResponseDto
import retrofit2.http.GET

interface HomeApiService {
    @GET("cdn/home/public/api/v1/get-launcher/tv-android")
    suspend fun getLauncherHome(): LauncherResponseDto
}
