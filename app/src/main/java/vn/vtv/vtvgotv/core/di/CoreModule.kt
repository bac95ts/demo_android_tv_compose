package vn.vtv.vtvgotv.core.di

import android.util.Log
import vn.vtv.vtvgotv.core.network.NetworkConfig
import vn.vtv.vtvgotv.core.network.AuthInterceptor
import vn.vtv.vtvgotv.core.network.RetryInterceptor
import vn.vtv.vtvgotv.core.network.LoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val coreModule = module {
    single {
        AuthInterceptor(
            onRequest = { requestBuilder ->
                // Injects default headers
                requestBuilder.addHeader("Content-Type", "application/json")
                requestBuilder.addHeader("Accept", "application/json")

                // onRequest event callback: inject dynamic token
                // e.g. requestBuilder.addHeader("Authorization", "Bearer <cached_token>")
                Log.d("CoreModule", "🔑 [onRequest] Injecting authorization headers dynamically.")
            },
            onError = { response ->
                // onError event callback: catches HTTP failures
                Log.e("CoreModule", "⚠️ [onError] Request failed with API error status: ${response.code}")
                if (response.code == 401) {
                    Log.e("CoreModule", "🔄 [onError] Detected 401 Unauthorized! Launching refresh token flow...")
                    // Refresh token synchronous logic goes here
                }
            }
        )
    }

    single { RetryInterceptor() }
    
    single { LoggingInterceptor() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<RetryInterceptor>())
            .addInterceptor(get<LoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
