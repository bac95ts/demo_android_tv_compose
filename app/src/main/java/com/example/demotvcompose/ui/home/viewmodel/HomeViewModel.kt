package com.example.demotvcompose.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demotvcompose.data.api.ApiService
import com.example.demotvcompose.data.model.LauncherItem
import com.example.demotvcompose.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _launcherItems = MutableStateFlow<List<LauncherItem>>(emptyList())
    val launcherItems: StateFlow<List<LauncherItem>> = _launcherItems

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchLauncherItems()
    }

    private fun fetchLauncherItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _launcherItems.value = repository.getLauncherItems()
            _isLoading.value = false
        }
    }
}