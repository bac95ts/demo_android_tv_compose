package vn.vtv.vtvgotv.features.home.data

import vn.vtv.vtvgotv.features.home.data.remote.HomeRemote
import vn.vtv.vtvgotv.features.home.domain.HomeRepository
import vn.vtv.vtvgotv.features.home.domain.model.LauncherItemModel
import vn.vtv.vtvgotv.features.home.domain.model.toModel

class HomeRepositoryImpl(private val homeRemote: HomeRemote) : HomeRepository {
    override suspend fun getLauncherItems(): List<LauncherItemModel> {
        return try {
            val response = homeRemote.getLauncherHomeRemote()
            response.data?.map { dto ->
                dto.toModel()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
