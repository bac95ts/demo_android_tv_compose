package com.example.demotvcompose.data.dto

data class LauncherResponseDto(
    val title: String?,
    val data: List<LauncherItemDto>?
)

data class LauncherItemDto(
    val _id: String?,
    val title: String?,
    val image: String?
)
