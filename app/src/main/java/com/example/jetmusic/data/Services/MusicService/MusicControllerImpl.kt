package com.example.jetmusic.data.Services.MusicService

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.mapper.toMediaItem
import com.example.jetmusic.data.mapper.toMusic
import com.example.jetmusic.domain.service.MusicController
import com.example.jetmusic.states.PlayerState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicControllerImpl(context: Context) : MusicController {

    private var mediaControllerFuture: ListenableFuture<MediaController>
    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: (
        (
        playerState: PlayerState,
        currentMusic: MusicObject?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit
    )? = null

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, MusicService::class.java))
        mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({ controllerListener() }, MoreExecutors.directExecutor())
    }


    private fun controllerListener() {
        mediaController?.addListener(
            object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    CoroutineScope(Dispatchers.IO).launch {}
                    with(player) {
                        mediaControllerCallback?.invoke(
                            playbackState.toPlayerState(isPlaying),
                            currentMediaItem?.toMusic(),
                            currentPosition.coerceAtLeast(0L),
                            duration.coerceAtLeast(0L),
                            shuffleModeEnabled,
                            repeatMode == Player.REPEAT_MODE_ONE
                        )
                    }
                }
            }
        )
    }

    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.STOPPED
            Player.STATE_ENDED -> PlayerState.STOPPED
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    override fun setMediaItem(musicObject: MusicObject) {
        mediaController?.setMediaItem(musicObject.toMediaItem())
    }

    override fun clearMediaItems() {
        mediaController?.clearMediaItems()
    }

    override fun setMediaItems(musicList: List<MusicObject>) {
        val filteredMedia = musicList.filter { it.image != null}
        val mediaItems = filteredMedia.map { it.toMediaItem() }

        mediaController?.setMediaItems(mediaItems)
    }

    override fun select(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)

            prepare()
        }
    }

    override fun resume() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun getCurrentPosition(): Long = mediaController?.currentPosition ?: 0L

    override fun getCurrentMusic(): MusicObject? = mediaController?.currentMediaItem?.toMusic()

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }

    override fun skipToNextSong() {
        mediaController?.seekToNext()
    }

    override fun skipToPreviousSong() {
        mediaController?.seekToPrevious()
    }

}