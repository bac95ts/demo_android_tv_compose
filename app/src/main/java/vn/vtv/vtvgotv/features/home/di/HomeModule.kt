package vn.vtv.vtvgotv.features.home.di

import vn.vtv.vtvgotv.features.home.data.remote.api.HomeApiService
import vn.vtv.vtvgotv.features.home.data.remote.HomeRemote
import vn.vtv.vtvgotv.features.home.data.remote.HomeRemoteImpl
import vn.vtv.vtvgotv.features.home.data.HomeRepositoryImpl
import vn.vtv.vtvgotv.features.home.domain.HomeRepository
import vn.vtv.vtvgotv.features.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single {
        get<Retrofit>().create(HomeApiService::class.java)
    }

    single<HomeRemote> {
        HomeRemoteImpl(get())
    }

    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }

    viewModel {
        HomeViewModel(get())
    }
}
