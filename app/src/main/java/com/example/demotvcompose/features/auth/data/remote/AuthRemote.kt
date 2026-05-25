package com.example.demotvcompose.features.auth.data.remote

/**
 * Remote data source interface for Auth (for future API integrations)
 */
interface AuthRemote {
    suspend fun loginRemote(email: String, password: String): Boolean
}
