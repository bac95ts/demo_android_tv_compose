package vn.vtv.vtvgotv.di

import vn.vtv.vtvgotv.core.di.coreModule
import vn.vtv.vtvgotv.features.auth.di.authModule
import vn.vtv.vtvgotv.features.home.di.homeModule
import vn.vtv.vtvgotv.features.player.di.playerModule
import vn.vtv.vtvgotv.features.search.di.searchModule

/**
 * Global AppModule aggregator.
 * Combines all core and feature Koin modules into a unified list.
 */
val appModule = listOf(
    coreModule,
    authModule,
    homeModule,
    playerModule,
    searchModule
)
