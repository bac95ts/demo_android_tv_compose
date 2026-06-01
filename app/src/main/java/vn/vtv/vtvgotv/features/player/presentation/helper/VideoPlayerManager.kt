package vn.vtv.vtvgotv.features.player.presentation.helper

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
class VideoPlayerManager {
    private var exoPlayer: ExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null

    val player: ExoPlayer? get() = exoPlayer

    fun initialize(context: Context, playerView: PlayerView, hlsUrl: String, adTagUrl: String) {
        if (exoPlayer != null) return // Already initialized

        val adsLoaderInstance = ImaAdsLoader.Builder(context).build()
        adsLoader = adsLoaderInstance

        val mediaSourceFactory = DefaultMediaSourceFactory(context)
            .setLocalAdInsertionComponents({ adsLoaderInstance }, playerView)

        val playerInstance = ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        exoPlayer = playerInstance

        // Bind PlayerView and AdsLoader to the Player
        playerView.player = playerInstance
        adsLoaderInstance.setPlayer(playerInstance)

        // Setup MediaItem with Ads Configuration
        val adTagUri = Uri.parse(adTagUrl)
        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse(hlsUrl))
            .setAdsConfiguration(
                MediaItem.AdsConfiguration.Builder(adTagUri).build()
            )
            .build()

        playerInstance.setMediaItem(mediaItem)
        playerInstance.prepare()
        playerInstance.playWhenReady = true
    }

    fun release() {
        adsLoader?.setPlayer(null)
        exoPlayer?.release()
        adsLoader?.release()
        exoPlayer = null
        adsLoader = null
    }
}
