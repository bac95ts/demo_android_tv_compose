package vn.vtv.vtvgotv.features.player.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.vtv.vtvgotv.features.player.presentation.helper.VideoPlayerManager

class PlayerViewModel(
    private val playerManager: VideoPlayerManager
) : ViewModel() {

    private val _showOsd = MutableStateFlow(true)
    val showOsd: StateFlow<Boolean> = _showOsd.asStateFlow()

    private var osdTimerJob: Job? = null

    fun initializePlayer(context: Context, playerView: PlayerView, hlsUrl: String, adTagUrl: String) {
        playerManager.initialize(context, playerView, hlsUrl, adTagUrl)
    }

    fun releasePlayer(playerView: PlayerView) {
        playerView.player = null
        playerManager.release()
    }

    fun startOsdTimer() {
        osdTimerJob?.cancel()
        osdTimerJob = viewModelScope.launch {
            _showOsd.value = true
            delay(4000)
            _showOsd.value = false
        }
    }

    fun showOsdBriefly() {
        startOsdTimer()
    }

    fun hideOsd() {
        osdTimerJob?.cancel()
        _showOsd.value = false
    }

    override fun onCleared() {
        super.onCleared()
        osdTimerJob?.cancel()
    }
}
