package com.example.demotvcompose.data.repository

import android.content.Context
import androidx.core.content.edit

/**
 * Repository to manage user local caching & account state
 */
class AccountRepository(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("vtv_preferences", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit { putBoolean("isLogined", isLoggedIn) }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLogined", false)
    }
}
