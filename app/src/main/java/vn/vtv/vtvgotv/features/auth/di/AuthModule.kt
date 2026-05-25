package vn.vtv.vtvgotv.features.auth.di

import vn.vtv.vtvgotv.features.auth.data.AuthRepositoryImpl
import vn.vtv.vtvgotv.features.auth.domain.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(androidContext())
    }
}
