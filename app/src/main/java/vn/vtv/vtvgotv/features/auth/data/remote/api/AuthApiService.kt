package vn.vtv.vtvgotv.features.auth.data.remote.api

import retrofit2.http.POST

/**
 * Retrofit API Service for Auth endpoints (for future integration)
 */
interface AuthApiService {
    @POST("api/v1/auth/login")
    suspend fun login(): Any
}
