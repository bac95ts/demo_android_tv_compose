package vn.vtv.vtvgotv.core.utils

import android.content.Context
import android.widget.Toast

/**
 * Utility extensions for Android Context
 */

/**
 * Display a short toast message safely
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Quick SharedPreferences helper
 */
fun Context.getAppPreferences(name: String = "vtv_preferences") =
    getSharedPreferences(name, Context.MODE_PRIVATE)
