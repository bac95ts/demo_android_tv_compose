package vn.vtv.vtvgotv.features.home.data.remote

import vn.vtv.vtvgotv.features.home.data.remote.model.response.LauncherResponseDto

interface HomeRemote {
    suspend fun getLauncherHomeRemote(): LauncherResponseDto
}
