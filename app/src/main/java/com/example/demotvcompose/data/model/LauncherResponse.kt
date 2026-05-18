package com.example.demotvcompose.data.model

data class LauncherResponse(
    val title: String?,
    val data: List<LauncherItem>?
)

data class LauncherItem(
    val _id: String,
    val title: String,
    val image: String
)
