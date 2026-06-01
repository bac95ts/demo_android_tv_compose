package vn.vtv.vtvgotv.features.player.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.vtv.vtvgotv.features.player.presentation.helper.VideoPlayerManager
import vn.vtv.vtvgotv.features.player.presentation.viewmodel.PlayerViewModel

/**
 * Player injection bindings
 */
val playerModule = module {
    single { VideoPlayerManager() }
    viewModel { PlayerViewModel(get()) }
}
