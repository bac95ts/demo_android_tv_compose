package vn.vtv.vtvgotv.core.network

import vn.vtv.vtvgotv.env.VtvEnvironment

/**
 * Global Network Configuration constants for Retrofit / OkHttpClient.
 * Delegates BASE_URL to flavor-specific VtvEnvironment.
 */
object NetworkConfig {
    const val BASE_URL = VtvEnvironment.BASE_URL
}
