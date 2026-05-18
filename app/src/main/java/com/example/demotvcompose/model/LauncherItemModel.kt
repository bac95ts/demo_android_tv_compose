package com.example.demotvcompose.model

import com.example.demotvcompose.data.dto.LauncherItemDto

data class LauncherItemModel(
    val id: String,
    val title: String,
    val image: String
) {
    companion object {
        fun fromDto(dto: LauncherItemDto): LauncherItemModel {
            return LauncherItemModel(
                id = dto._id.orEmpty(),
                title = dto.title.orEmpty(),
                image = dto.image.orEmpty()
            )
        }
    }
}
