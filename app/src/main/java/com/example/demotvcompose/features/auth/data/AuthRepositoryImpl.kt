package com.example.demotvcompose.features.auth.data

import android.content.Context
import androidx.core.content.edit
import com.example.demotvcompose.features.auth.domain.AuthRepository

/**
 * Data layer implementation of AuthRepository using local SharedPreferences cache
 */
class AuthRepositoryImpl(private val context: Context) : AuthRepository {
    private val sharedPreferences = context.getSharedPreferences("vtv_preferences", Context.MODE_PRIVATE)

    override fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit { putBoolean("isLogined", isLoggedIn) }
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLogined", false)
    }
}
