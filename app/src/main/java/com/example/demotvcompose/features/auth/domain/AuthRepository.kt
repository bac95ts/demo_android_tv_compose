package com.example.demotvcompose.features.auth.domain

/**
 * Domain layer repository for authentication operations
 */
interface AuthRepository {
    fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
}
