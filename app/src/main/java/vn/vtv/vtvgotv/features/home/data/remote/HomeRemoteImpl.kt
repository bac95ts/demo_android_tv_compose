package vn.vtv.vtvgotv.features.home.data.remote

import vn.vtv.vtvgotv.features.home.data.remote.api.HomeApiService
import vn.vtv.vtvgotv.features.home.data.remote.model.response.LauncherResponseDto

class HomeRemoteImpl(private val homeApiService: HomeApiService) : HomeRemote {
    override suspend fun getLauncherHomeRemote(): LauncherResponseDto {
        return homeApiService.getLauncherHome()
    }
}
