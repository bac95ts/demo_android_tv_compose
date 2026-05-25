package com.example.demotvcompose.di

import com.example.demotvcompose.data.api.ApiService
import com.example.demotvcompose.data.repository.HomeRepository
import com.example.demotvcompose.data.repository.AccountRepository
import com.example.demotvcompose.ui.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://cache-api-vtvgo.vtvdigital.vn/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single {
        HomeRepository(get())
    }

    single {
        AccountRepository(androidContext())
    }

    viewModel {
        HomeViewModel(get())
    }
}
