package com.example.demotvcompose.features.auth.data.remote

/**
 * Remote data source implementation for Auth (Mocked)
 */
class AuthRemoteImpl : AuthRemote {
    override suspend fun loginRemote(email: String, password: String): Boolean {
        return true
    }
}
