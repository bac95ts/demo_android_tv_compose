package com.example.demotvcompose.di

import com.example.demotvcompose.features.home.data.remote.api.HomeApiService
import com.example.demotvcompose.features.home.data.remote.HomeRemote
import com.example.demotvcompose.features.home.data.remote.HomeRemoteImpl
import com.example.demotvcompose.features.home.data.HomeRepositoryImpl
import com.example.demotvcompose.features.home.domain.HomeRepository
import com.example.demotvcompose.features.auth.data.AuthRepositoryImpl
import com.example.demotvcompose.features.auth.domain.AuthRepository
import com.example.demotvcompose.features.home.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.demotvcompose.core.network.NetworkConfig
import com.example.demotvcompose.core.network.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(HomeApiService::class.java)
    }

    single<HomeRemote> {
        HomeRemoteImpl(get())
    }

    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(androidContext())
    }

    viewModel {
        HomeViewModel(get())
    }
}
