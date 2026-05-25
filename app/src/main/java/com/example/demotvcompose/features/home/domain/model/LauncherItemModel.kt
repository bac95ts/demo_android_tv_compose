package com.example.demotvcompose.features.home.domain.model

import com.example.demotvcompose.features.home.data.remote.model.response.LauncherItemDto


/**
 * Domain-level representation of a Launcher Media Item
 */
data class LauncherItemModel(
    val id: String,
    val title: String,
    val image: String
)

fun LauncherItemDto.toModel(): LauncherItemModel {
    return LauncherItemModel(
        id = this._id.orEmpty(),
        title = this.title.orEmpty(),
        image = this.image.orEmpty()
    )
}

